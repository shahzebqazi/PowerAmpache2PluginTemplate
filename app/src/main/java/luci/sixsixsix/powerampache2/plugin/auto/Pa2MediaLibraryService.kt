/**
 * Media3 [MediaLibraryService] for Android Auto: browse playlists and album sections,
 * play streams via ExoPlayer when [luci.sixsixsix.powerampache2.plugin.domain.model.Song.songUrl] is set.
 *
 * Data flows through [luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher] only; the host app
 * must bind to [luci.sixsixsix.powerampache2.plugin.PA2DataFetchService] so fetches reach the listener.
 */
package luci.sixsixsix.powerampache2.plugin.auto

import android.content.Intent
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionError
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.model.Album
import luci.sixsixsix.powerampache2.plugin.domain.model.Playlist
import luci.sixsixsix.powerampache2.plugin.R
import luci.sixsixsix.powerampache2.plugin.domain.model.Song
import javax.inject.Inject

@AndroidEntryPoint
class Pa2MediaLibraryService : MediaLibraryService() {

    @Inject lateinit var musicFetcher: MusicFetcher
    @Inject lateinit var applicationScope: CoroutineScope

    private var player: ExoPlayer? = null
    private var librarySession: MediaLibrarySession? = null

    @UnstableApi
    override fun onCreate() {
        super.onCreate()
        val exoPlayer = ExoPlayer.Builder(applicationContext).build().also { player = it }
        val callback = Pa2LibraryCallback()
        librarySession = MediaLibrarySession.Builder(this, exoPlayer, callback).build()
        subscribeToLibraryChanges()
    }

    private fun subscribeToLibraryChanges() {
        val session = librarySession ?: return
        applicationScope.launch {
            musicFetcher.playlistsFlow.collect {
                session.notifyChildrenChanged(MediaIds.ROOT, 0, null)
                session.notifyChildrenChanged(MediaIds.SECTION_PLAYLISTS, 0, null)
            }
        }
        applicationScope.launch {
            musicFetcher.favouriteAlbumsFlow.collect {
                session.notifyChildrenChanged(MediaIds.ROOT, 0, null)
                session.notifyChildrenChanged(MediaIds.SECTION_FAVOURITE_ALBUMS, 0, null)
            }
        }
        applicationScope.launch {
            musicFetcher.recentAlbumsFlow.collect {
                session.notifyChildrenChanged(MediaIds.ROOT, 0, null)
                session.notifyChildrenChanged(MediaIds.SECTION_RECENT_ALBUMS, 0, null)
            }
        }
        applicationScope.launch {
            musicFetcher.latestAlbumsFlow.collect {
                session.notifyChildrenChanged(MediaIds.ROOT, 0, null)
                session.notifyChildrenChanged(MediaIds.SECTION_LATEST_ALBUMS, 0, null)
            }
        }
        applicationScope.launch {
            musicFetcher.highRatedAlbumsFlow.collect {
                session.notifyChildrenChanged(MediaIds.ROOT, 0, null)
                session.notifyChildrenChanged(MediaIds.SECTION_HIGHEST_RATED_ALBUMS, 0, null)
            }
        }
        applicationScope.launch {
            musicFetcher.playlistSongsMapFlow.collect {
                it.keys.forEach { pid ->
                    session.notifyChildrenChanged(MediaIds.playlist(pid), 0, null)
                }
            }
        }
        applicationScope.launch {
            musicFetcher.albumSongsMapFlow.collect {
                it.keys.forEach { aid ->
                    session.notifyChildrenChanged(MediaIds.album(aid), 0, null)
                }
            }
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaLibrarySession? =
        librarySession

    override fun onDestroy() {
        librarySession?.run {
            player?.release()
            release()
        }
        librarySession = null
        player = null
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        player?.let {
            if (!it.playWhenReady || it.playbackState == Player.STATE_ENDED) {
                stopSelf()
            }
        } ?: stopSelf()
    }

    @UnstableApi
    private inner class Pa2LibraryCallback : MediaLibrarySession.Callback {

        override fun onGetLibraryRoot(
            session: MediaLibrarySession,
            browser: MediaSession.ControllerInfo,
            params: MediaLibraryService.LibraryParams?
        ): ListenableFuture<LibraryResult<MediaItem>> {
            val root = browsableItem(
                MediaIds.ROOT,
                getString(R.string.media_browse_root_title)
            )
            return Futures.immediateFuture(LibraryResult.ofItem(root, params))
        }

        override fun onGetChildren(
            session: MediaLibrarySession,
            browser: MediaSession.ControllerInfo,
            parentId: String,
            page: Int,
            pageSize: Int,
            params: MediaLibraryService.LibraryParams?
        ): ListenableFuture<LibraryResult<ImmutableList<MediaItem>>> {
            return when (parentId) {
                MediaIds.ROOT -> immediateChildren(rootSections(), params)
                MediaIds.SECTION_PLAYLISTS -> immediateChildren(
                    musicFetcher.playlistsFlow.value.map { playlistItem(it) },
                    params
                )
                MediaIds.SECTION_FAVOURITE_ALBUMS -> immediateChildren(
                    musicFetcher.favouriteAlbumsFlow.value.map { albumItem(it) },
                    params
                )
                MediaIds.SECTION_RECENT_ALBUMS -> immediateChildren(
                    musicFetcher.recentAlbumsFlow.value.map { albumItem(it) },
                    params
                )
                MediaIds.SECTION_LATEST_ALBUMS -> immediateChildren(
                    musicFetcher.latestAlbumsFlow.value.map { albumItem(it) },
                    params
                )
                MediaIds.SECTION_HIGHEST_RATED_ALBUMS -> immediateChildren(
                    musicFetcher.highRatedAlbumsFlow.value.map { albumItem(it) },
                    params
                )
                else -> {
                    MediaIds.parsePlaylistId(parentId)?.let { pid ->
                        return playlistChildrenFuture(pid, params)
                    }
                    MediaIds.parseAlbumId(parentId)?.let { aid ->
                        return albumChildrenFuture(aid, params)
                    }
                    Futures.immediateFuture(LibraryResult.ofError(SessionError.ERROR_BAD_VALUE))
                }
            }
        }

        override fun onGetItem(
            session: MediaLibrarySession,
            browser: MediaSession.ControllerInfo,
            mediaId: String,
        ): ListenableFuture<LibraryResult<MediaItem>> {
            val item = when {
                mediaId == MediaIds.ROOT -> browsableItem(
                    MediaIds.ROOT,
                    getString(R.string.media_browse_root_title)
                )
                mediaId == MediaIds.SECTION_PLAYLISTS -> sectionItem(
                    MediaIds.SECTION_PLAYLISTS,
                    getString(R.string.media_section_playlists)
                )
                mediaId == MediaIds.SECTION_FAVOURITE_ALBUMS -> sectionItem(
                    MediaIds.SECTION_FAVOURITE_ALBUMS,
                    getString(R.string.media_section_favourite_albums)
                )
                mediaId == MediaIds.SECTION_RECENT_ALBUMS -> sectionItem(
                    MediaIds.SECTION_RECENT_ALBUMS,
                    getString(R.string.media_section_recent_albums)
                )
                mediaId == MediaIds.SECTION_LATEST_ALBUMS -> sectionItem(
                    MediaIds.SECTION_LATEST_ALBUMS,
                    getString(R.string.media_section_newest_albums)
                )
                mediaId == MediaIds.SECTION_HIGHEST_RATED_ALBUMS -> sectionItem(
                    MediaIds.SECTION_HIGHEST_RATED_ALBUMS,
                    getString(R.string.media_section_highest_rated_albums)
                )
                else -> {
                    val pid = MediaIds.parsePlaylistId(mediaId)
                    if (pid != null) {
                        musicFetcher.playlistsFlow.value.find { it.id == pid }?.let { playlistItem(it) }
                    } else {
                        val aid = MediaIds.parseAlbumId(mediaId)
                        if (aid != null) {
                            findAlbum(aid)?.let { albumItem(it) }
                        } else {
                            val sid = MediaIds.parseSongId(mediaId)
                            if (sid != null) findSong(sid)?.let { songToPlayableMediaItem(it) } else null
                        }
                    }
                }
            }
            return if (item != null) {
                Futures.immediateFuture(LibraryResult.ofItem(item, params = null))
            } else {
                Futures.immediateFuture(LibraryResult.ofError(SessionError.ERROR_BAD_VALUE))
            }
        }

        private fun immediateChildren(
            items: List<MediaItem>,
            params: MediaLibraryService.LibraryParams?
        ): ListenableFuture<LibraryResult<ImmutableList<MediaItem>>> =
            Futures.immediateFuture(
                LibraryResult.ofItemList(ImmutableList.copyOf(items), params)
            )

        private fun rootSections(): List<MediaItem> = listOf(
            sectionItem(
                MediaIds.SECTION_PLAYLISTS,
                getString(R.string.media_section_playlists)
            ),
            sectionItem(
                MediaIds.SECTION_FAVOURITE_ALBUMS,
                getString(R.string.media_section_favourite_albums)
            ),
            sectionItem(
                MediaIds.SECTION_RECENT_ALBUMS,
                getString(R.string.media_section_recent_albums)
            ),
            sectionItem(
                MediaIds.SECTION_LATEST_ALBUMS,
                getString(R.string.media_section_newest_albums)
            ),
            sectionItem(
                MediaIds.SECTION_HIGHEST_RATED_ALBUMS,
                getString(R.string.media_section_highest_rated_albums)
            ),
        )

        private fun sectionItem(id: String, title: String): MediaItem = browsableItem(id, title)

        private fun browsableItem(id: String, title: String): MediaItem =
            MediaItem.Builder()
                .setMediaId(id)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(title)
                        .setIsBrowsable(true)
                        .setIsPlayable(false)
                        .build()
                )
                .build()

        private fun playlistItem(p: Playlist): MediaItem =
            MediaItem.Builder()
                .setMediaId(MediaIds.playlist(p.id))
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(p.name)
                        .setIsBrowsable(true)
                        .setIsPlayable(false)
                        .build()
                )
                .build()

        private fun albumItem(a: Album): MediaItem =
            MediaItem.Builder()
                .setMediaId(MediaIds.album(a.id))
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(a.name)
                        .setSubtitle(a.artist.name)
                        .setIsBrowsable(true)
                        .setIsPlayable(false)
                        .build()
                )
                .build()

        private fun findAlbum(albumId: String): Album? {
            val inList: (List<Album>) -> Album? = { list -> list.find { it.id == albumId } }
            return inList(musicFetcher.favouriteAlbumsFlow.value)
                ?: inList(musicFetcher.recentAlbumsFlow.value)
                ?: inList(musicFetcher.latestAlbumsFlow.value)
                ?: inList(musicFetcher.highRatedAlbumsFlow.value)
                ?: inList(musicFetcher.albumsFlow.value)
        }

        private fun findSong(songId: String): Song? {
            musicFetcher.albumSongsMapFlow.value.values.forEach { songs ->
                songs.find { it.id == songId || it.mediaId == songId }?.let { return it }
            }
            musicFetcher.playlistSongsMapFlow.value.values.forEach { songs ->
                songs.find { it.id == songId || it.mediaId == songId }?.let { return it }
            }
            musicFetcher.currentQueueFlow.value.find { it.id == songId || it.mediaId == songId }?.let {
                return it
            }
            return null
        }

        private fun playlistChildrenFuture(
            playlistId: String,
            params: MediaLibraryService.LibraryParams?
        ): ListenableFuture<LibraryResult<ImmutableList<MediaItem>>> {
            return CallbackToFutureAdapter.getFuture { completer ->
                applicationScope.launch {
                    val songs: List<Song> = runCatching {
                        val cached = musicFetcher.playlistSongsMapFlow.value[playlistId]
                        if (!cached.isNullOrEmpty()) {
                            cached
                        } else {
                            musicFetcher.getSongsFromPlaylist(playlistId)
                            withTimeoutOrNull(FETCH_TIMEOUT_MS) {
                                musicFetcher.playlistSongsMapFlow
                                    .map { it[playlistId] ?: emptyList() }
                                    .distinctUntilChanged()
                                    .first { it.isNotEmpty() }
                            } ?: emptyList()
                        }
                    }.getOrDefault(emptyList())
                    completer.set(
                        LibraryResult.ofItemList(
                            ImmutableList.copyOf(songs.map { songToPlayableMediaItem(it) }),
                            params
                        )
                    )
                }
                "playlistChildren-$playlistId"
            }
        }

        private fun albumChildrenFuture(
            albumId: String,
            params: MediaLibraryService.LibraryParams?
        ): ListenableFuture<LibraryResult<ImmutableList<MediaItem>>> {
            return CallbackToFutureAdapter.getFuture { completer ->
                applicationScope.launch {
                    val songs: List<Song> = runCatching {
                        val cached = musicFetcher.albumSongsMapFlow.value[albumId]
                        if (!cached.isNullOrEmpty()) {
                            cached
                        } else {
                            musicFetcher.getSongsFromAlbum(albumId)
                            withTimeoutOrNull(FETCH_TIMEOUT_MS) {
                                musicFetcher.albumSongsMapFlow
                                    .map { it[albumId] ?: emptyList() }
                                    .distinctUntilChanged()
                                    .first { it.isNotEmpty() }
                            } ?: emptyList()
                        }
                    }.getOrDefault(emptyList())
                    completer.set(
                        LibraryResult.ofItemList(
                            ImmutableList.copyOf(songs.map { songToPlayableMediaItem(it) }),
                            params
                        )
                    )
                }
                "albumChildren-$albumId"
            }
        }

        private fun songToPlayableMediaItem(song: Song): MediaItem {
            val meta = MediaMetadata.Builder()
                .setTitle(song.title)
                .setArtist(song.artist.name)
                .setAlbumTitle(song.album.name)
                .setIsBrowsable(false)
                .setIsPlayable(true)
                .build()
            val builder = MediaItem.Builder()
                .setMediaId(MediaIds.song(song.id))
                .setMediaMetadata(meta)
            val url = song.songUrl
            if (url.isNotBlank()) {
                builder.setUri(url)
            }
            return builder.build()
        }
    }

    companion object {
        private const val FETCH_TIMEOUT_MS = 8_000L
    }
}
