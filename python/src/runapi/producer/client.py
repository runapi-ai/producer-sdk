"""Producer client."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import ProviderClient

from .resources.text_to_music import TextToMusic


class ProducerClient(ProviderClient):
    """Producer FUZZ music generation client."""

    def __init__(self, api_key: Optional[str] = None, **options: Any) -> None:
        super().__init__(api_key, **options)
        http = self._http
        self.text_to_music = TextToMusic(http)
