<div align="center">

<img src="docs/images/dhu-now-playing.png" alt="Android Auto now playing in Desktop Head Unit (DHU)" width="720" />

# Power Ampache 2 — Android Auto Plugin

**Official Android Auto extension for [Power Ampache 2](https://github.com/icefields/Power-Ampache-2)**

Android · Media3 · Material You (handheld shell)

</div>

Bring **Power Ampache 2** to the car: browse albums, playlists, favorites, and recent items on the **Android Auto** media browser, with playback through a **Media3** `MediaLibraryService` / `MediaLibrarySession`. The **head unit** draws lists and transport controls; this plugin supplies a solid **`MediaItem`** tree, metadata, artwork, and session behaviour aligned with [Google’s car media guidance](https://developer.android.com/training/cars/media).

Works with the same **Ampache / Nextcloud Music–compatible** backends as Power Ampache 2 (Ampache API 4+). Pair it with the main app on your phone for the full PA2 experience; this repository is the **Android Auto–focused** plugin and engineering home for that integration.

* * *

## Features

- **Library browsing on Auto** — Favorites, recent albums, highest-rated albums, playlists, and latest albums exposed as a predictable browse tree.
- **Media3-first** — `MediaLibraryService`, **ExoPlayer**, **MediaLibrarySession**; async browse via `ListenableFuture` and coroutines (Guava bridge).
- **Shared look & feel on phone** — **PowerAmpache2Theme** ([official PA2 theme](https://github.com/icefields/PowerAmpache2Theme)) for the handheld Compose surfaces in this package.
- **Clean Architecture** — `domain`, `data`, `app`, and `PowerAmpache2Theme` modules; Hilt for DI.

**🚗 ANDROID AUTO INTEGRATION**  
Standard **media browser** path: discovery, queue, shuffle/repeat where the platform exposes them — no custom Android Auto UI templates; compliance is about **data**, **session**, and **browse** quality on the host.

**🔧 DEVELOPER-FRIENDLY**  
Kotlin, Jetpack Compose (app shell), Media3 session APIs, and docs for UX research and user stories live under `docs/`.

* * *

## Documentation

| Topic | Link |
| ----- | ---- |
| User stories (Android Auto MVP) | [docs/user-stories.md](docs/user-stories.md) |
| UX research (Android Auto + PA2) | [docs/ux-research/README.md](docs/ux-research/README.md) |
| Agent & contributor workflow (Kanban, architecture) | [AGENTS.md](AGENTS.md) |

* * *

## Requirements

- **minSdk 30** · **targetSdk 36** (see `app/build.gradle.kts`)
- **Android Studio** and a configured **Android SDK** for local builds
- For in-car validation: **Android Auto** docs, **DHU** or hardware, as appropriate

* * *

## Build

```bash
./gradlew :app:assembleDebug
```

Use **Android Studio** to run the `app` module on a device or emulator. Android Auto behaviour is validated against **Media3** session and browse contracts (see [AGENTS.md](AGENTS.md) for module map and Auto service entry point).

* * *

## Contributing

This project follows **Clean Architecture** and the conventions in [**AGENTS.md**](AGENTS.md). Before changing **application code**, align work with the [**Power Ampache 2 — Android Auto Plugin** GitHub Project](https://github.com/users/shahzebqazi/projects/7) (Kanban). Mockups, documentation under `docs/`, and GitHub Pages edits may proceed without that step unless the maintainer adds them to the board.

For broader **Power Ampache 2** contribution practices, see the main app’s guidelines: [power.ampache.dev/contributing.html](https://power.ampache.dev/contributing.html).

* * *

## Related projects

| Project | Role |
| ------- | ---- |
| [**Power-Ampache-2**](https://github.com/icefields/Power-Ampache-2) | Main Android music client (Material You, phone UI, core playback). |
| [**PowerAmpache2Theme**](https://github.com/icefields/PowerAmpache2Theme) | Shared Compose theme used by PA2 and this plugin. |
| [**PowerAmpache2PluginTemplate**](https://github.com/icefields/PowerAmpache2PluginTemplate) (upstream) | Upstream plugin template this project tracks. |

* * *

## Ending note

- If this plugin is useful to you, consider giving the repository a star and supporting **[Power Ampache 2](https://github.com/icefields/Power-Ampache-2)**.
