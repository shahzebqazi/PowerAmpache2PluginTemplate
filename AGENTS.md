# Agents — Power Ampache 2 plugin template

This document orients AI and human contributors. Update it when workflows change.

## GitHub Projects Kanban (required for code)

All agents **must** use the Kanban **before** editing any **code** in this repository (application source, modules, tests, Gradle/build scripts, CI config when it changes builds, etc.).

- **Board:** [Power Ampache 2 - Android Auto Plugin](https://github.com/users/shahzebqazi/projects/7) — GitHub Projects (user project #7, `shahzebqazi`).
- **Workflow:** Confirm the work is tracked on the board (existing card/issue or maintainer-approved representation). Do **not** begin code changes until the task is aligned with the Kanban per project practice.

**Exceptions — Kanban not required** (unless the maintainer adds the work to the board):

- **Mockups** (e.g. Figma-linked assets, mockup branches, design-only deliverables).
- **Documentation** under `docs/` and similar doc-only edits.
- **GitHub Pages** content or Pages-related configuration.

If unsure whether a change counts as “code,” default to using the Kanban first.

## Repository and branches

- **Upstream template (maintainer):** `icefields/PowerAmpache2PluginTemplate` on GitHub. If `git fetch` fails with “repository not found,” the repo may be private: authenticate (HTTPS token or SSH) or ask the maintainer for access.
- **This remote:** `origin` — develop feature work here unless directed otherwise.
- **`main`:** Default integration branch for this repo (includes app implementation and docs merged from former `dev`).
- **Topic branches:** e.g. `plugin/AndroidAuto`, `mockups` — preserve for reference unless the maintainer approves removal.

## Scope and boundaries

- **Modules:** This template uses **`domain`**, **`data`**, **`app`**, and **`PowerAmpache2Theme`**. Respect Clean Architecture boundaries; expand scope only when the product owner agrees.
- **Architecture:** Agents assist with coding but do not make architectural decisions without explicit human approval. Propose options; the human chooses.
- **Theme / UI:** Use **PA2Theme** (`PowerAmpache2Theme` module) for UI. Align Figma and implementation with those tokens where applicable.

## Requirements and design

1. **User stories (MVP):** [docs/user-stories.md](docs/user-stories.md). Refine functional and non-functional requirements with the maintainer.
2. **UX research:** [docs/ux-research/README.md](docs/ux-research/README.md) and related files under `docs/`.
3. Treat maintainer-provided **real** use cases as authoritative. Do not ship features solely because template examples show them; remove or replace misleading examples when implementation begins.

## Tracking and testing

- **GitHub Projects:** Follow [GitHub Projects Kanban (required for code)](#github-projects-kanban-required-for-code). Keep issues/cards in sync with actual work.
- A **developer-supplied APK** may be available for manual testing when local builds are blocked.

## Tooling: Android Auto and MCP

- **Android Auto:** Prefer **Android Studio**, **Android for Cars** docs, and device/DHU testing. No Android Auto–specific MCP is assumed in this workspace; other MCPs (e.g. Figma) are optional. Document any new MCP here if added.

## Figma and mockups

- Mockups should reflect **approved user stories** and PA2Theme visual language where possible.
- Optional research assets may live on a separate branch (e.g. `mockups`) if you keep docs-only work out of `main`.

---

## Technical handoff — codebase

### Modules

| Module | Role |
|--------|------|
| `domain` | Models, `MusicFetcher`, use cases (`StateFlow` / `Flow`). |
| `data` | `MusicFetcherImpl` and DI; binds domain interfaces. |
| `app` | Application, Compose UI, **Android Auto** (`Media3`). |
| `PowerAmpache2Theme` | Shared Compose theme. |

### Android Auto — Media3 (app layer)

- **Service:** `app/.../androidauto/AndroidAutoMediaLibraryService.kt` — `MediaLibraryService` + **ExoPlayer** + **MediaLibrarySession**.
- **Browse contract:** Root `[root]`; five folders under it: `[favorites]`, `[recent_albums]`, `[highest_albums]`, `[playlists]`, `[latest_albums]`. Collections use ids `album_{id}` / `playlist_{id}`; tracks from `MusicFetcher` flows (first emission).
- **Async:** `MediaLibrarySession.Callback` returns `ListenableFuture`; bridge with **`kotlinx.coroutines.guava`** (`future { }` on a `CoroutineScope`).
- **Commands:** Default session+library and default player commands (shuffle/repeat). No custom AA UI overlays — only `MediaItem` / `MediaMetadata`.
- **Mapping:** `MediaItemMapping.kt`, `MediaLibraryIds.kt`. **Manifest:** intent action `androidx.media3.session.MediaLibraryService`.

### Domain API (media browsing)

- List use cases: `FavouriteAlbumStateFlow`, `RecentAlbumsStateFlow`, `HighestAlbumsStateFlow`, `PlaylistsStateFlow`, `LatestAlbumsStateFlow` — `StateFlow` lists (some support `useMock` for **domain previews only**; the Auto service does not enable mocks).
- Songs: `MusicFetcher.getSongsFromAlbum` / `getSongsFromPlaylist` → **`Flow<List<Song>>`**.
- **Queue:** `QueueStateFlow` → **`musicFetcher.currentQueueFlow` only** (no mock path). `SongListViewModel` binds that `StateFlow` directly.

### Data layer

- `MusicFetcherImpl` resolves tracks from in-memory state; unknown ids may yield **empty** lists — not fake success lists. Some methods may remain `TODO` until wired.

### Dependencies

- Media3: `media3-session`, `media3-exoplayer` (`gradle/libs.versions.toml`).
- Coroutines: `kotlinx-coroutines-core`, `kotlinx-coroutines-guava`, **Guava**.

### Conventions

- Minimal, task-scoped changes; match Hilt and existing style.
- Do **not** add app-layer “mock success” for production Auto behavior.
- Run **`./gradlew :app:assembleDebug`** when an Android SDK is configured.

### Suggested next steps

1. Wire `MusicFetcherImpl` to real Ampache/network data.
2. Decide `LibraryResult.ofError` vs empty lists for browse failures.
3. Foreground service / notification if policy requires it for playback.
4. Optional Media3 browse integration tests.

---

*Last updated: Kanban workflow for agents + Media3 integration alignment.*
