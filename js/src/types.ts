import type { AsyncTaskStatus } from '@runapi.ai/core';

export type ProducerModel = 'fuzz-2.0';
export type VocalMode = 'exact_lyrics' | 'instrumental';
export type GenerationStage = 'all_audios_ready' | 'failed';

interface TextToMusicBaseParams {
  /** Producer FUZZ model slug. */
  model: ProducerModel;
  /** Music style and production brief, from 1 to 200 characters. */
  prompt: string;
  /** Optional title for the generated song. */
  title?: string;
  /** URL to receive a webhook notification when the task completes. */
  callback_url?: string;
}

export interface ExactLyricsParams extends TextToMusicBaseParams {
  vocal_mode: 'exact_lyrics';
  /** Exact lyrics to sing. */
  lyrics: string;
}

export interface InstrumentalParams extends TextToMusicBaseParams {
  vocal_mode: 'instrumental';
  lyrics?: never;
}

export type TextToMusicParams = ExactLyricsParams | InstrumentalParams;

export interface TaskCreateResponse {
  id: string;
  status?: AsyncTaskStatus;
}

export interface Audio {
  id?: string;
  audio_url?: string;
  image_url?: string;
  model_name?: ProducerModel;
  title?: string;
  duration_seconds?: number;
  lyrics?: string;
}

export interface TextToMusicResponse {
  id: string;
  status: AsyncTaskStatus;
  audios?: Audio[];
  generation_stage?: GenerationStage;
  error?: string;
  [key: string]: unknown;
}

export type CompletedTextToMusicResponse = TextToMusicResponse & {
  status: 'completed';
  audios: Audio[];
};
