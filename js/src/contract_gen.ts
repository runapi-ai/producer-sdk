export const contract = {
  "text-to-music": {
    "models": [
      "fuzz-2.0"
    ],
    "fields_by_model": {
      "fuzz-2.0": {
        "model": {
          "required": true
        },
        "prompt": {
          "required": true,
          "min": 1,
          "max": 200,
          "length": true
        },
        "vocal_mode": {
          "enum": [
            "exact_lyrics",
            "instrumental"
          ],
          "required": true
        }
      }
    },
    "rules": [
      {
        "when": {
          "vocal_mode": "exact_lyrics"
        },
        "required": [
          "lyrics"
        ]
      },
      {
        "when": {
          "vocal_mode": "instrumental"
        },
        "forbidden": [
          "lyrics"
        ]
      }
    ]
  }
} as const;
