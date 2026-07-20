CONTRACT = {
    "text-to-music": {
        "models": ["fuzz-2.0"],
        "fields_by_model": {
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
