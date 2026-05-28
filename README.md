<div align="center">

<p align="center">
  <img src="docs/assets/hero.svg" alt="Power Ampache 2 — Android Auto plugin template" width="100%" />
</p>

# Power Ampache 2 — Plugin Template & Android Auto

### Kotlin companion app for in-car browse and playback via Power Ampache 2

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-7F52FF.svg)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-Auto%20%2B%20Media3-3DDC84.svg)](https://developer.android.com/media/media3)
[![License: MIT](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

**Android Auto plugin template** for self-hosted [Ampache](https://ampache.org/) libraries. The plugin is an **IPC client** of [Power Ampache 2](https://github.com/icefields/PowerAmpache2) — it does not call the Ampache API directly.

> **Scope:** Template + reference implementation (Clean Architecture: `domain`, `data`, `app`, `PowerAmpache2Theme`). **UX research sandbox:** [pa2-car-plugin on GitHub Pages](https://shahzebqazi.github.io/pa2-car-plugin/).

[Architecture](#architecture-overview) · [Data flow](#data-flow) · [Build](#build--run) · [UX demo](#ux-demo) · [Stack](#libraries)

</div>

---

## Architecture overview

```
┌─────────────────────┐    Messenger IPC    ┌──────────────────────────┐
│   Power Ampache 2    │ ◄─────────────────► │  Plugin (this app)       │
│   (host app)         │   (bidirectional)   │                          │
│                     │                     │  PA2DataFetchService     │
│   - Ampache API     │ ─── sends data ───► │   └─ MusicFetcherImpl   │
│   - Stream URLs     │                     │       └─ StateFlows     │
│   - Playback queue  │                     │                          │
│                     │ ◄── requests data ── │  Pa2MediaLibraryService │
│                     │                     │   └─ ExoPlayer           │
│                     │                     │   └─ Media3 Session      │
└─────────────────────┘                     └──────────────────────────┘
                                                      │
                                              Android Auto (head unit)
                                              browses via MediaLibraryService
```

---

## Data flow

1. **Plugin binds to host.** `Pa2MediaLibraryService` starts and binds to `PA2DataFetchService`. The host registers via Messenger `register_client`.

2. **Host pushes library JSON.** Playlists, albums, artists, and songs arrive as Gson-deserialized payloads into `MusicFetcherImpl` `StateFlow`s.

3. **Plugin requests on browse.** Android Auto drill-down triggers domain use cases → `MusicFetcherListener` → Messenger request → host responds asynchronously.

4. **Media3 browse tree.** `onGetLibraryRoot`, `onGetChildren`, `onGetItem` expose playlists, favourites, recents, and albums to the car UI.

5. **ExoPlayer playback.** `onAddMediaItems` / `onSetMediaItems` resolve `mediaId` back to stream URLs; single-song taps expand to album/playlist queues for skip/next.

6. **Queue mirroring.** When the phone plays in the host app, the plugin mirrors the host queue into ExoPlayer (paused) so Android Auto shows Now Playing metadata.

---

## UX demo

In-car layout and browse UX experiments (no device required):

**→ [shahzebqazi.github.io/pa2-car-plugin/](https://shahzebqazi.github.io/pa2-car-plugin/)**

Use this sandbox when explaining MediaLibraryService navigation without installing the full host + head-unit stack.

---

## Build & run

```bash
git clone https://github.com/shahzebqazi/PowerAmpache2PluginTemplate.git
cd PowerAmpache2PluginTemplate
./gradlew :app:assembleDebug
```

Install the debug APK alongside a Power Ampache 2 host build that exposes the plugin Messenger API. Android Auto testing: Desktop Head Unit (DHU) or physical head unit with developer mode.

---

## Libraries

| Layer | Technology |
|-------|------------|
| Playback / browse | Media3, ExoPlayer, `MediaLibraryService` |
| DI | Dagger Hilt |
| Serialization | Gson |
| Phone UI | Jetpack Compose, Material 3 |

---

## Related

| Resource | URL |
|----------|-----|
| Portfolio CV | [sqazi.sh/content.html?page=cv](https://sqazi.sh/content.html?page=cv) |
| UX sandbox repo | [pa2-car-plugin](https://shahzebqazi.github.io/pa2-car-plugin/) |
| Upstream host | [icefields/PowerAmpache2](https://github.com/icefields/PowerAmpache2) |

---

## License

MIT — see [LICENSE](LICENSE).  
Author: [Willy Worst](https://sqazi.sh) · code@sqazi.sh
