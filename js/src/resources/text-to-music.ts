import type { ActionSchema, HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams, validateParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import { contract } from '../contract_gen';
import type {
  CompletedTextToMusicResponse,
  TaskCreateResponse,
  TextToMusicParams,
  TextToMusicResponse,
} from '../types';

const ENDPOINT = '/api/v1/producer/text_to_music';

/** Generate a FUZZ song from an exact-lyrics or instrumental brief. */
export class TextToMusic {
  constructor(private readonly http: HttpClient) {}

  async run(
    params: TextToMusicParams,
    options?: RequestOptions & PollingOptions,
  ): Promise<CompletedTextToMusicResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<TextToMusicResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedTextToMusicResponse;
  }

  async create(params: TextToMusicParams, options?: RequestOptions): Promise<TaskCreateResponse> {
    const body = compactParams(params);
    validateParams(contract['text-to-music'] as ActionSchema, body as Record<string, unknown>);
    return this.http.request<TaskCreateResponse>('POST', ENDPOINT, { body, ...options });
  }

  async get(id: string, options?: RequestOptions): Promise<TextToMusicResponse> {
    return this.http.request<TextToMusicResponse>('GET', `${ENDPOINT}/${id}`, { ...options });
  }
}
