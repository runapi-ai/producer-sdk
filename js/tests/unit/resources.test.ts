import { beforeEach, describe, expect, it, vi } from 'vitest';
import type { HttpClient } from '@runapi.ai/core';
import { TextToMusic } from '../../src/resources/text-to-music';

describe('Producer TextToMusic', () => {
  const mockHttp: HttpClient = { request: vi.fn() };

  beforeEach(() => vi.clearAllMocks());

  it('creates exact-lyrics tasks with flat public params', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-1', status: 'processing' });
    const resource = new TextToMusic(mockHttp);

    await resource.create({
      model: 'fuzz-2.0',
      vocal_mode: 'exact_lyrics',
      prompt: 'Warm acoustic pop with clear vocals',
      lyrics: '[Verse] Morning light',
      title: 'Morning Light',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/producer/text_to_music', {
      body: {
        model: 'fuzz-2.0',
        vocal_mode: 'exact_lyrics',
        prompt: 'Warm acoustic pop with clear vocals',
        lyrics: '[Verse] Morning light',
        title: 'Morning Light',
      },
    });
  });

  it('gets tasks and decodes normalized audio results', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({
      id: 'task-1',
      status: 'completed',
      audios: [{
        id: 'audio-1',
        audio_url: 'https://media.runapi.ai/google-storage/producer-app-public/clips/audio.m4a',
        duration_seconds: 78.35,
      }],
    });
    const resource = new TextToMusic(mockHttp);

    const result = await resource.get('task-1');

    expect(mockHttp.request).toHaveBeenCalledWith('GET', '/api/v1/producer/text_to_music/task-1', {});
    expect(result.audios?.[0]?.id).toBe('audio-1');
    expect(result.audios?.[0]?.duration_seconds).toBe(78.35);
  });
});
