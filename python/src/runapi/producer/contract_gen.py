CONTRACT = {
    "text-to-music": {
        "models": ["fuzz-0.8", "fuzz-1.0", "fuzz-1.0-pro", "fuzz-1.1", "fuzz-1.1-pro", "fuzz-2.0", "fuzz-2.0-pro", "fuzz-2.0-raw"],
        "fields_by_model": {
            "fuzz-0.8": {
                "model": {
                    "required": True
                },
                "prompt": {
                    "required": True,
                    "min": 1,
                    "max": 200,
                    "length": True
                },
                "vocal_mode": {
                    "enum": ["exact_lyrics", "instrumental"],
                    "required": True
                }
            },
            "fuzz-1.0": {
                "model": {
                    "required": True
                },
                "prompt": {
                    "required": True,
                    "min": 1,
                    "max": 200,
                    "length": True
                },
                "vocal_mode": {
                    "enum": ["exact_lyrics", "instrumental"],
                    "required": True
                }
            },
            "fuzz-1.0-pro": {
                "model": {
                    "required": True
                },
                "prompt": {
                    "required": True,
                    "min": 1,
                    "max": 200,
                    "length": True
                },
                "vocal_mode": {
                    "enum": ["exact_lyrics", "instrumental"],
                    "required": True
                }
            },
            "fuzz-1.1": {
                "model": {
                    "required": True
                },
                "prompt": {
                    "required": True,
                    "min": 1,
                    "max": 200,
                    "length": True
                },
                "vocal_mode": {
                    "enum": ["exact_lyrics", "instrumental"],
                    "required": True
                }
            },
            "fuzz-1.1-pro": {
                "model": {
                    "required": True
                },
                "prompt": {
                    "required": True,
                    "min": 1,
                    "max": 200,
                    "length": True
                },
                "vocal_mode": {
                    "enum": ["exact_lyrics", "instrumental"],
                    "required": True
                }
            },
            "fuzz-2.0": {
                "model": {
                    "required": True
                },
                "prompt": {
                    "required": True,
                    "min": 1,
                    "max": 200,
                    "length": True
                },
                "vocal_mode": {
                    "enum": ["exact_lyrics", "instrumental"],
                    "required": True
                }
            },
            "fuzz-2.0-pro": {
                "model": {
                    "required": True
                },
                "prompt": {
                    "required": True,
                    "min": 1,
                    "max": 200,
                    "length": True
                },
                "vocal_mode": {
                    "enum": ["exact_lyrics", "instrumental"],
                    "required": True
                }
            },
            "fuzz-2.0-raw": {
                "model": {
                    "required": True
                },
                "prompt": {
                    "required": True,
                    "min": 1,
                    "max": 200,
                    "length": True
                },
                "vocal_mode": {
                    "enum": ["exact_lyrics", "instrumental"],
                    "required": True
                }
            }
        },
        "rules": [{
            "when": {
                "vocal_mode": "exact_lyrics"
            },
            "required": ["lyrics"]
        }, {
            "when": {
                "vocal_mode": "instrumental"
            },
            "forbidden": ["lyrics"]
        }]
    }
}
