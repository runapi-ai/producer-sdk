"""Producer response models."""

from __future__ import annotations

from runapi.core import BaseModel, TaskResponse, optional, required


class Audio(BaseModel):
    """A generated audio track."""

    id = optional(str)
    audio_url = optional(str)
    image_url = optional(str)
    model_name = optional(str)
    title = optional(str)
    duration_seconds = optional(float)
    lyrics = optional(str)


class TextToMusicResponse(TaskResponse):
    """Producer text-to-music task status response."""

    id = required(str)
    status = optional(str, enum=lambda: TaskResponse.Status.ALL)
    audios = optional([lambda: Audio])
    generation_stage = optional(str)
    error = optional(str)


class CompletedTextToMusicResponse(TextToMusicResponse):
    """Narrowed response from ``run()`` once polling observes completion."""

    audios = required([lambda: Audio])
