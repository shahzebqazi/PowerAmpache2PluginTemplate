# Agent handoff — Power Ampache 2 plugin template

This document orients future coding agents and maintainers working in this repository.

## Project

Kotlin Android plugin template for **Power Ampache 2**. Modules:

| Module | Role |
|--------|------|
| `domain` | Clean Architecture domain: models, `MusicFetcher`, use cases (`StateFlow` / `Flow`). |
| `data` | `MusicFetcherImpl` and DI; binds domain interfaces. |
| `app` | Application, Compose UI, **Android Auto** integration (`Media3`). |
| `PowerAmpache2Theme` | Shared Compose theme. |

## Android Auto — Media3 (app layer)

- **Service**: `app/.../androidauto/AndroidAutoMediaLibraryService.kt` — extends `androidx.media3.session.MediaLibraryService`, holds **ExoPlayer** + **MediaLibrarySession**.
- **Browse contract**: Root id `[root]`; children of `[root]` are exactly five folders: `[favorites]`, `[recent_albums]`, `[highest_albums]`, `[playlists]`, `[latest_albums]`. Collections map to browsable items with ids `album_{id}` / `playlist_{id}`; tracks come from `MusicFetcher` flows (first emission).
- **Async**: `MediaLibrarySession.Callback` returns `ListenableFuture`; use **`kotlinx.coroutines.guava`** (`future { }` on a `CoroutineScope`) for work that must bridge coroutines and Guava.
- **Commands**: Connection uses default session+library commands and default player commands (shuffle/repeat included). No custom Android Auto UI; only `MediaItem` / `MediaMetadata`.
- **Mapping**: `MediaItemMapping.kt`, ids in `MediaLibraryIds.kt`.
- **Manifest**: `AndroidAutoMediaLibraryService` is registered with intent action `androidx.media3.session.MediaLibraryService`.

## Domain API (relevant to media browsing)

- Album/playlist lists: `FavouriteAlbumStateFlow`, `RecentAlbumsStateFlow`, `HighestAlbumsStateFlow`, `PlaylistsStateFlow`, `LatestAlbumsStateFlow` — each `invoke(...): StateFlow<List<...>>` (some use cases still support `useMock` for **domain-level** previews; the Android Auto service calls them with defaults and does not enable mocks).
- Songs: `MusicFetcher.getSongsFromAlbum(albumId)` / `getSongsFromPlaylist(playlistId)` return **`Flow<List<Song>>`**.
- **Queue**: `QueueStateFlow` exposes **`musicFetcher.currentQueueFlow` only** (no mock branch). App `SongListViewModel` binds that `StateFlow` directly.

## Data layer

- `MusicFetcherImpl` resolves album tracks from in-memory album state flows; playlists from `playlistsFlow`. Unknown ids may yield **empty** lists — not fake success lists. `getAlbumsFromArtist` may still be unimplemented (`TODO`) until wired.

## Dependencies (Gradle)

- Media3: `media3-session`, `media3-exoplayer` (see `gradle/libs.versions.toml`).
- Coroutines: `kotlinx-coroutines-core`, `kotlinx-coroutines-guava`, plus **Guava** for `ListenableFuture` interop.

## Branches

- **`main`**: Code intended for upstream; includes Media3 service and **no** `mockups/` research folder in-repo.
- **`mockups`**: Optional branch for **research assets** (e.g. Figma links, written notes) if maintained separately.

## Conventions for agents

- Prefer **minimal, task-scoped** changes; match existing style and DI (Hilt).
- Do **not** add app-layer “mock success” paths for production features; domain `useMock` flags are for **tests/previews**, not shipped Auto behavior.
- After substantive changes, run **`./gradlew :app:assembleDebug`** (or CI) when an Android SDK is available (`ANDROID_HOME` / `local.properties`).

## Handoff — suggested next steps

1. **Wire `MusicFetcherImpl`** to real Ampache/network sources so album and playlist flows return non-empty data when the host app has populated state.
2. **Error handling** in `MediaLibrarySession.Callback`: decide when to return `LibraryResult.ofError` vs empty lists (e.g. network failure).
3. **Foreground service / notification** if long playback or background policy requires it (evaluate against target SDK).
4. **Integration tests** for browse tree (optional) using Media3 test utilities or a small harness.
5. **Update `README.md`** for contributors if this template gains more surface area.

---

*Last updated on main branch during Android Auto Media3 integration work.*
