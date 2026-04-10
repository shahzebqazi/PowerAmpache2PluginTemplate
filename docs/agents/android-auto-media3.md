# Android Auto — Media3 implementation handoff (app layer)

Technical notes for coding agents and maintainers working on **`AndroidAutoMediaLibraryService`** and related code **on this branch**.

## Project layout

| Module | Role |
|--------|------|
| `domain` | Models, `MusicFetcher`, use cases (`StateFlow` / `Flow`). |
| `data` | `MusicFetcherImpl` and DI. |
| `app` | Application, Compose UI, Android Auto (`Media3`). |
| `PowerAmpache2Theme` | Shared Compose theme. |

## Service and browse contract

- **Service:** `app/.../androidauto/AndroidAutoMediaLibraryService.kt` — extends `androidx.media3.session.MediaLibraryService`, holds **ExoPlayer** + **MediaLibrarySession**.
- **Root:** id `[root]`; children of `[root]` are five folders: `[favorites]`, `[recent_albums]`, `[highest_albums]`, `[playlists]`, `[latest_albums]`.
- **Collections:** browsable items with ids `album_{id}` / `playlist_{id}`; tracks from `MusicFetcher` flows (first emission).
- **Async:** `MediaLibrarySession.Callback` returns `ListenableFuture`; use **`kotlinx.coroutines.guava`** (`future { }` on a `CoroutineScope`) to bridge coroutines and Guava.
- **Commands:** Session/player defaults include shuffle/repeat where applicable. No custom Android Auto UI — only `MediaItem` / `MediaMetadata`.
- **Mapping:** `MediaItemMapping.kt`, ids in `MediaLibraryIds.kt`.
- **Manifest:** service registered with `androidx.media3.session.MediaLibraryService`.

## Domain API (browsing)

- **Album/playlist lists:** `FavouriteAlbumStateFlow`, `RecentAlbumsStateFlow`, `HighestAlbumsStateFlow`, `PlaylistsStateFlow`, `LatestAlbumsStateFlow` — `StateFlow`-based use cases (some support `useMock` for domain previews; the Auto service should not enable mocks in production paths).
- **Songs:** `MusicFetcher.getSongsFromAlbum` / `getSongsFromPlaylist` → `Flow<List<Song>>`.
- **Queue:** `QueueStateFlow` / `musicFetcher.currentQueueFlow` as wired in the app.

## Data layer

- `MusicFetcherImpl` resolves tracks from album/playlist state; unknown ids may yield **empty** lists.

## Dependencies

- Media3: `media3-session`, `media3-exoplayer` (`gradle/libs.versions.toml`).
- Coroutines + **kotlinx-coroutines-guava**, Guava for `ListenableFuture`.

## Conventions

- Prefer **minimal, task-scoped** changes; match existing style and DI (Hilt).
- Avoid app-layer “mock success” for production Auto behaviour.
- After substantive changes, run **`./gradlew :app:assembleDebug`** when an Android SDK is available.

## Suggested next steps

1. Wire `MusicFetcherImpl` to real network/Ampache sources where applicable.
2. Error handling in callbacks: `LibraryResult.ofError` vs empty lists.
3. Foreground service / notification if policy requires it.
4. Integration tests for browse tree (optional).

---

*Align with product; this file describes the template as implemented on the mockups branch.*
