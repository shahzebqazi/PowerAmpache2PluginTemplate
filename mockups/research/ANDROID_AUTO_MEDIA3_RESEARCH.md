# Android Auto — Media3 adapter (research)

This document captures the architecture and contracts for the **app-layer** Android Auto integration using **AndroidX Media3** (`media3-session`, `media3-exoplayer`). The **domain layer** stays unchanged in role: it exposes Kotlin coroutines (`StateFlow`, `Flow`); the service bridges to **Guava `ListenableFuture`** expected by `MediaLibrarySession.Callback`.

## Stack

- Kotlin
- `androidx.media3:media3-session`, `androidx.media3:media3-exoplayer`
- `kotlinx-coroutines-guava`: `kotlinx.coroutines.guava.future` inside callbacks that must return `ListenableFuture<LibraryResult<...>>`

## Browse contract

| Callback | Behavior |
|----------|----------|
| `onGetLibraryRoot` | Single item: `mediaId = "[root]"`, browsable, not playable. |
| `onGetChildren("[root]")` | Exactly five browsable folders: `[favorites]`, `[recent_albums]`, `[highest_albums]`, `[playlists]`, `[latest_albums]`. Folder type: mixed (mapped as `MEDIA_TYPE_FOLDER_MIXED`). |
| `onGetChildren(dashboard id)` | Read the matching `StateFlow`’s **current value**, map `Album` / `Playlist` to browsable `MediaItem`s with ids `album_{id}` / `playlist_{id}`. |
| `onGetChildren("album_*" / "playlist_*")` | Strip prefix, call `MusicFetcher.getSongsFromAlbum` / `getSongsFromPlaylist`, take **first** `Flow` emission, map `Song` to playable items. |

## Domain API (given shape)

- `FavouriteAlbumStateFlow`, `RecentAlbumsStateFlow`, `HighestAlbumsStateFlow`, `PlaylistsStateFlow`, `LatestAlbumsStateFlow`: `invoke(useMock: Boolean): StateFlow<List<...>>`
- `musicFetcher.getSongsFromAlbum(albumId): Flow<List<Song>>`
- `musicFetcher.getSongsFromPlaylist(playlistId): Flow<List<Song>>`

## Implementation notes

- **No custom Android Auto UI**: only `MediaItem` / `MediaMetadata`; the host renders browse and Now Playing.
- **Shuffle / repeat**: session advertises default session+library commands and default player commands (includes shuffle and repeat modes).
- **Async**: collections and track loads use `future(Dispatchers.IO) { ... }` so the callback returns a `ListenableFuture` without blocking the main thread.

## Figma

See `FIGMA_LINKS.txt` in this folder for the design file and FigJam diagram URLs.
