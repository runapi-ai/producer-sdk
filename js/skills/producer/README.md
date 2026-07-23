<h3 align="center">Producer API Skill for RunAPI</h3>

<div align="center">

[![skills.sh](https://www.skills.sh/b/runapi-ai/producer)](https://www.skills.sh/runapi-ai/producer/producer)
[![ClawHub](https://img.shields.io/badge/ClawHub-runapi--producer-111827)](https://clawhub.ai/runapi-ai/runapi-producer)
[![License](https://img.shields.io/github/license/runapi-ai/producer)](https://github.com/runapi-ai/producer/blob/main/LICENSE)

</div>
<br/>

Use Producer through the RunAPI CLI for one-off FUZZ music generation from exact lyrics or instrumental briefs. The canonical agent file is skills/producer/SKILL.md.

## Install

Install for Codex, Claude Code, Gemini CLI, Cursor, and other supported agents:

~~~bash
npx skills add runapi-ai/producer -g
~~~

Or paste this prompt to your AI agent:

~~~text
Install the producer skill for me:

1. Clone https://github.com/runapi-ai/producer
2. Copy the skills/producer/ directory into your user-level skills directory.
3. Verify that SKILL.md is present.
4. Confirm the install path when done.
~~~

Claude Code can also install the repository skill directory into its user skills directory. Codex and Gemini CLI should use their user-level skills directories and keep SKILL.md at skills/producer/SKILL.md.

## Commands

~~~shell
runapi producer text-to-music --input-file text-to-music.json
runapi producer text-to-music --async --input-file text-to-music.json
runapi wait <task-id> --service producer --action text-to-music
~~~

Use exact_lyrics with lyrics, or instrumental without lyrics.

## Variants

- [FUZZ 2.0](https://runapi.ai/models/producer/fuzz-2.0)
- [FUZZ 2.0 Pro](https://runapi.ai/models/producer/fuzz-2.0-pro)
- [FUZZ 2.0 Raw](https://runapi.ai/models/producer/fuzz-2.0-raw)
- [FUZZ 1.1 Pro](https://runapi.ai/models/producer/fuzz-1.1-pro)
- [FUZZ 1.0 Pro](https://runapi.ai/models/producer/fuzz-1.0-pro)
- [FUZZ 1.0](https://runapi.ai/models/producer/fuzz-1.0)
- [FUZZ 1.1](https://runapi.ai/models/producer/fuzz-1.1)
- [FUZZ 0.8](https://runapi.ai/models/producer/fuzz-0.8)

## Links

- Model page: https://runapi.ai/models/producer
- Producer model details and pricing: https://runapi.ai/models/producer/fuzz-2.0
- Product docs: https://runapi.ai/docs#producer
- SDK repository: https://github.com/runapi-ai/producer-sdk
- Provider page: https://runapi.ai/providers/producer
- Full catalog: https://runapi.ai/models

## License

Licensed under the Apache License, Version 2.0.
