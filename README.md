<p align="center">
  <a href="https://runapi.ai"><img src="https://runapi.ai/icon.svg" height="56" alt="RunAPI"></a>
</p>

<h3 align="center">
  <a href="https://github.com/runapi-ai/producer-sdk">Producer FUZZ API SDK for RunAPI</a>
</h3>

<p align="center">
  Producer FUZZ music generation SDKs for JavaScript, Python, Ruby, Go, Java, and PHP on RunAPI.
</p>

<div align="center">

[![npm](https://img.shields.io/npm/v/@runapi.ai/producer)](https://www.npmjs.com/package/@runapi.ai/producer)
[![PyPI](https://img.shields.io/pypi/v/runapi-producer)](https://pypi.org/project/runapi-producer/)
[![RubyGems](https://img.shields.io/gem/v/runapi-producer)](https://rubygems.org/gems/runapi-producer)
[![Go Reference](https://pkg.go.dev/badge/github.com/runapi-ai/producer-sdk/go.svg)](https://pkg.go.dev/github.com/runapi-ai/producer-sdk/go)
[![Maven Central](https://img.shields.io/maven-central/v/ai.runapi/runapi-producer)](https://central.sonatype.com/artifact/ai.runapi/runapi-producer)
[![License](https://img.shields.io/github/license/runapi-ai/producer-sdk)](https://github.com/runapi-ai/producer-sdk/blob/main/LICENSE)

</div>
<br/>

The Producer SDK provides typed clients for generating FUZZ music with exact lyrics or an instrumental production brief. The public `producer-sdk` repository groups the non-PHP language packages, examples, CI, and language release tags. The PHP package is released from a split Composer repository.

## Install

~~~bash
npm install @runapi.ai/producer
pip install runapi-producer
gem install runapi-producer
go get github.com/runapi-ai/producer-sdk/go@latest
~~~

Gradle:

~~~kotlin
dependencies {
  implementation("ai.runapi:runapi-producer:0.1.0")
}
~~~

The PHP package is published from the split Composer repository as `runapi-ai/producer`; see https://github.com/runapi-ai/producer-php for PHP install and examples.

## JavaScript quick start

~~~typescript
import { ProducerClient } from '@runapi.ai/producer';

const client = new ProducerClient();
const result = await client.textToMusic.run({
  model: 'fuzz-2.0',
  vocal_mode: 'exact_lyrics',
  prompt: 'Warm acoustic pop with clear vocals',
  lyrics: '[Verse]\nMorning light across the room',
  title: 'Morning Light',
});

console.log(result.audios[0].audio_url);
~~~

Use vocal_mode exact_lyrics with lyrics, or instrumental without lyrics. Use create, get, and run to submit, inspect, or create-and-poll a task.

## Repository layout

- `js/` publishes `@runapi.ai/producer`.
- `python/` publishes `runapi-producer`.
- `ruby/` publishes `runapi-producer`.
- `go/` publishes `github.com/runapi-ai/producer-sdk/go`.
- `java/` publishes `ai.runapi:runapi-producer`.

Generated media URLs are temporary. Download and store results in your own durable storage.

## Links

- Model page: https://runapi.ai/models/producer
- FUZZ 2.0 details and pricing: https://runapi.ai/models/producer
- Product docs: https://runapi.ai/docs#producer
- SDK docs: https://runapi.ai/docs#sdk-producer
- PHP package repository: https://github.com/runapi-ai/producer-php
- Provider page: https://runapi.ai/providers/producer
- Full catalog: https://runapi.ai/models

## License

Licensed under the Apache License, Version 2.0.
