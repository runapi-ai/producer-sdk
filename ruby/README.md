# Producer Ruby SDK for RunAPI

Use runapi-producer for FUZZ music generation in Ruby applications and workers.

## Install

~~~bash
gem install runapi-producer
~~~

## Quick Start

~~~ruby
require "runapi/producer"

client = RunApi::Producer::Client.new
result = client.text_to_music.run(
  model: "fuzz-2.0",
  vocal_mode: "instrumental",
  prompt: "Cinematic ambient score with warm analog synths"
)
puts result.audios.first.audio_url
~~~

Use create, get, and run for asynchronous tasks. Use exact_lyrics with lyrics, or instrumental without lyrics. Generated media URLs are temporary and should be stored in durable storage.

## Links

- Model page: https://runapi.ai/models/producer
- Product docs: https://runapi.ai/docs/api/producer/text-to-music
- SDK docs: https://runapi.ai/docs/resources/sdks
- FUZZ 2.0 details and pricing: https://runapi.ai/models/producer
- Repository: https://github.com/runapi-ai/producer-sdk

Licensed under the Apache License, Version 2.0.
