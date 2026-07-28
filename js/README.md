# Producer JavaScript SDK for RunAPI

Use @runapi.ai/producer for typed FUZZ music generation in JavaScript or TypeScript applications.

## Install

~~~bash
npm install @runapi.ai/producer
~~~

## Quick Start

~~~typescript
import { ProducerClient } from '@runapi.ai/producer';

const client = new ProducerClient();
const result = await client.textToMusic.run({
  model: 'fuzz-2.0',
  vocal_mode: 'instrumental',
  prompt: 'Cinematic ambient score with warm analog synths',
});
console.log(result.audios[0].audio_url);
~~~

Use create, get, and run for asynchronous tasks. Use exact_lyrics with lyrics, or instrumental without lyrics. Generated media URLs are temporary and should be stored in durable storage.

## Links

- Model page: https://runapi.ai/models/producer
- Product docs: https://runapi.ai/docs/api/producer/text-to-music
- SDK docs: https://runapi.ai/docs/resources/sdks
- FUZZ 2.0 details and pricing: https://runapi.ai/models/producer
- Repository: https://github.com/runapi-ai/producer-sdk

Licensed under the Apache License, Version 2.0.
