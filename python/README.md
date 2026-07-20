# Producer Python SDK for RunAPI

Use runapi-producer for FUZZ music generation in Python applications and workers.

## Install

~~~bash
pip install runapi-producer
~~~

## Quick Start

~~~python
from runapi.producer import ProducerClient

client = ProducerClient()
result = client.text_to_music.run(
    model="fuzz-2.0",
    vocal_mode="instrumental",
    prompt="Cinematic ambient score with warm analog synths",
)
print(result.audios[0].audio_url)
~~~

Use create, get, and run for asynchronous tasks. Use exact_lyrics with lyrics, or instrumental without lyrics. Generated media URLs are temporary and should be stored in durable storage.

## Links

- Model page: https://runapi.ai/models/producer
- Product docs: https://runapi.ai/docs#producer
- SDK docs: https://runapi.ai/docs#sdk-producer
- FUZZ 2.0 details and pricing: https://runapi.ai/models/producer
- Repository: https://github.com/runapi-ai/producer-sdk

Licensed under the Apache License, Version 2.0.
