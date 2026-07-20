import pytest

from runapi.core import config
from runapi.core.errors import AuthenticationError, ValidationError
from runapi.producer import ProducerClient
from runapi.producer.resources.text_to_music import TextToMusic
from runapi.producer.types import TextToMusicResponse


class FakeHttp:
    def __init__(self, *responses):
        self._responses = list(responses)
        self.calls = []

    def request(self, method, path, body=None, options=None):
        self.calls.append((method, path, body))
        if self._responses:
            return self._responses.pop(0)
        return {"id": "task-1", "status": "processing"}


@pytest.fixture(autouse=True)
def reset_config(monkeypatch):
    monkeypatch.delenv("RUNAPI_API_KEY", raising=False)
    monkeypatch.setattr(config, "api_key", None)
    yield


def test_accepts_api_key_and_exposes_resource():
    client = ProducerClient(api_key="key", http_client=FakeHttp())
    assert isinstance(client.text_to_music, TextToMusic)


def test_raises_without_api_key():
    with pytest.raises(AuthenticationError, match="API key is required"):
        ProducerClient()


def test_create_posts_flat_exact_lyrics_body():
    fake = FakeHttp({"id": "task-1", "status": "processing"})
    client = ProducerClient(api_key="key", http_client=fake)

    result = client.text_to_music.create(
        model="fuzz-2.0",
        vocal_mode="exact_lyrics",
        prompt="Warm acoustic pop with clear vocals",
        lyrics="[Verse] Morning light",
        title="Morning Light",
    )

    assert fake.calls == [
        (
            "post",
            "/api/v1/producer/text_to_music",
            {
                "model": "fuzz-2.0",
                "vocal_mode": "exact_lyrics",
                "prompt": "Warm acoustic pop with clear vocals",
                "lyrics": "[Verse] Morning light",
                "title": "Morning Light",
            },
        )
    ]
    assert isinstance(result, TextToMusicResponse)


def test_get_decodes_normalized_audio_results():
    fake = FakeHttp(
        {
            "id": "task-1",
            "status": "completed",
            "audios": [
                {
                    "id": "audio-1",
                    "audio_url": "https://media.runapi.ai/audio.m4a",
                    "duration_seconds": 78.35,
                }
            ],
        }
    )
    client = ProducerClient(api_key="key", http_client=fake)

    result = client.text_to_music.get("task-1")

    assert fake.calls == [("get", "/api/v1/producer/text_to_music/task-1", None)]
    assert result.audios[0].id == "audio-1"
    assert result.audios[0].duration_seconds == 78.35


def test_rejects_lyrics_in_instrumental_mode():
    client = ProducerClient(api_key="key", http_client=FakeHttp())

    with pytest.raises(ValidationError, match="lyrics is not allowed"):
        client.text_to_music.create(
            model="fuzz-2.0",
            vocal_mode="instrumental",
            prompt="Cinematic ambient score",
            lyrics="Should not be sent",
        )
