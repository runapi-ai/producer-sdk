# Producer Java SDK for RunAPI

[![Maven Central](https://img.shields.io/maven-central/v/ai.runapi/runapi-producer)](https://central.sonatype.com/artifact/ai.runapi/runapi-producer)

Use the Producer Java SDK for typed FUZZ music generation with exact lyrics or instrumental briefs.

## Requirements

The Java SDK targets Java 8 bytecode and is tested on Java 8, 11, 17, and 21.

## Install

Gradle:

```kotlin
dependencies {
  implementation("ai.runapi:runapi-producer:0.2.0")
}
```

Maven:

```xml
<dependency>
  <groupId>ai.runapi</groupId>
  <artifactId>runapi-producer</artifactId>
  <version>0.2.0</version>
</dependency>
```

## Quick Start

```java
import ai.runapi.producer.ProducerClient;
import ai.runapi.producer.types.CompletedTextToMusicResponse;
import ai.runapi.producer.types.TextToMusicModel;
import ai.runapi.producer.types.TextToMusicParams;

ProducerClient client = ProducerClient.builder()
    .apiKey(System.getenv("RUNAPI_API_KEY"))
    .build();

CompletedTextToMusicResponse result = client.textToMusic().run(
    TextToMusicParams.builder()
        .model(TextToMusicModel.FUZZ_2_0)
        .vocalMode("exact_lyrics")
        .prompt("Warm acoustic pop with clear vocals")
        .lyrics("[Verse]\nMorning light across the room")
        .title("Morning Light")
        .build()
);

System.out.println(result.getAudios().get(0).getAudioUrl());
```

Use `exact_lyrics` with `lyrics`, or `instrumental` without lyrics. `create(params)` submits a task, `get(id)` retrieves its latest state, and `run(params)` submits and polls until completion.

Completed results expose the audio URL, cover image URL, public model name, title, duration, lyrics, and generation stage. Generated media URLs are temporary; download and store them in durable storage.

## Links

- Model page: https://runapi.ai/models/producer
- SDK docs: https://runapi.ai/docs/resources/sdks
- Product docs: https://runapi.ai/docs/api/producer/text-to-music
- FUZZ 2.0 details and pricing: https://runapi.ai/models/producer
- Full catalog: https://runapi.ai/models
- Repository: https://github.com/runapi-ai/producer-sdk

## License

Licensed under the Apache License, Version 2.0.
