# Producer Go SDK for RunAPI

Use the Producer Go module for typed FUZZ music generation in Go services and command-line tools.

## Install

~~~bash
go get github.com/runapi-ai/producer-sdk/go@latest
~~~

## Quick Start

~~~go
client, err := producer.NewClient()
result, err := client.TextToMusic.Run(context.Background(), producer.TextToMusicParams{
  Model:     producer.ModelFuzz20,
  VocalMode: producer.VocalModeInstrumental,
  Prompt:    "Cinematic ambient score with warm analog synths",
})
fmt.Println(result.Audios[0].AudioURL)
~~~

Use Create, Get, and Run for asynchronous tasks. Use VocalModeExactLyrics with Lyrics, or VocalModeInstrumental without Lyrics. Generated media URLs are temporary and should be stored in durable storage.

## Links

- Model page: https://runapi.ai/models/producer
- Product docs: https://runapi.ai/docs/api/producer/text-to-music
- SDK docs: https://runapi.ai/docs/resources/sdks
- FUZZ 2.0 details and pricing: https://runapi.ai/models/producer
- Repository: https://github.com/runapi-ai/producer-sdk

Licensed under the Apache License, Version 2.0.
