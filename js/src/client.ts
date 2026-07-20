import { BaseClient, type ClientOptions } from '@runapi.ai/core';
import { TextToMusic } from './resources/text-to-music';

/** Producer FUZZ music generation client. */
export class ProducerClient extends BaseClient {
  /** Generate a song with exact lyrics or as an instrumental. */
  public readonly textToMusic: TextToMusic;

  constructor(options: ClientOptions = {}) {
    super(options);
    this.textToMusic = new TextToMusic(this.http);
  }
}
