package luci.sixsixsix.powerampache2.plugin.androidauto

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaLibraryService.LibraryParams
import androidx.media3.session.MediaLibraryService as Media3MediaLibraryService
import androidx.media3.session.MediaLibraryService.MediaLibrarySession
import androidx.media3.session.MediaSession
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.guava.future
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.usecase.FavouriteAlbumStateFlow
import luci.sixsixsix.powerampache2.plugin.domain.usecase.HighestAlbumsStateFlow
import luci.sixsixsix.powerampache2.plugin.domain.usecase.LatestAlbumsStateFlow
import luci.sixsixsix.powerampache2.plugin.domain.usecase.PlaylistsStateFlow
import luci.sixsixsix.powerampache2.plugin.domain.usecase.RecentAlbumsStateFlow
import javax.inject.Inject

@AndroidEntryPoint
class AndroidAutoMediaLibraryService : Media3MediaLibraryService() {

    @Inject lateinit var musicFetcher: MusicFetcher
    @Inject lateinit var favouriteAlbumStateFlow: FavouriteAlbumStateFlow
    @Inject lateinit var recentAlbumsStateFlow: RecentAlbumsStateFlow
    @Inject lateinit var highestAlbumsStateFlow: HighestAlbumsStateFlow
    @Inject lateinit var playlistsStateFlow: PlaylistsStateFlow
    @Inject lateinit var latestAlbumsStateFlow: LatestAlbumsStateFlow

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(serviceJob + Dispatchers.Main.immediate)

    private lateinit var player: ExoPlayer
    private lateinit var librarySession: MediaLibrarySession

    private val sessionCommands =
        MediaSession.ConnectionResult.DEFAULT_SESSION_AND_LIBRARY_COMMANDS

    private val playerCommands: Player.Commands =
        MediaSession.ConnectionResult.DEFAULT_PLAYER_COMMANDS

    private val libraryCallback = object : MediaLibrarySession.Callback() {

        override fun onConnect(
            session: MediaSession,
            controller: MediaSession.ControllerInfo
        ): MediaSession.ConnectionResult =
            MediaSession.ConnectionResult.accept(sessionCommands, playerCommands)

        override fun onGetLibraryRoot(
            session: MediaLibrarySession,
            browser: MediaSession.ControllerInfo,
            params: LibraryParams?
        ): ListenableFuture<LibraryResult<MediaItem>> {
            val root = MediaItem.Builder()
                .setMediaId(MediaLibraryIds.ROOT)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle("Power Ampache 2")
                        .setIsBrowsable(true)
                        .setIsPlayable(false)
                        .setMediaType(MediaMetadata.MEDIA_TYPE_FOLDER_MIXED)
                        .build()
                )
                .build()
            return Futures.immediateFuture(LibraryResult.ofItem(root, params))
        }

        override fun onGetChildren(
            session: MediaLibrarySession,
            browser: MediaSession.ControllerInfo,
            parentId: String,
            page: Int,
            pageSize: Int,
            params: LibraryParams?
        ): ListenableFuture<LibraryResult<ImmutableList<MediaItem>>> {
            when (parentId) {
                MediaLibraryIds.ROOT -> {
                    val items = listOf(
                        folderNode(MediaLibraryIds.FAVORITES, "Favorites"),
                        folderNode(MediaLibraryIds.RECENT_ALBUMS, "Recent albums"),
                        folderNode(MediaLibraryIds.HIGHEST_ALBUMS, "Highest rated albums"),
                        folderNode(MediaLibraryIds.PLAYLISTS, "Playlists"),
                        folderNode(MediaLibraryIds.LATEST_ALBUMS, "Latest albums"),
                    )
                    return Futures.immediateFuture(
                        LibraryResult.ofItemList(ImmutableList.copyOf(items), params)
                    )
                }
                MediaLibraryIds.FAVORITES -> return collectionFuture(
                    params
                ) { favouriteAlbumStateFlow().value.map { it.toBrowsableMediaItem() } }

                MediaLibraryIds.RECENT_ALBUMS -> return collectionFuture(
                    params
                ) { recentAlbumsStateFlow().value.map { it.toBrowsableMediaItem() } }

                MediaLibraryIds.HIGHEST_ALBUMS -> return collectionFuture(
                    params
                ) { highestAlbumsStateFlow().value.map { it.toBrowsableMediaItem() } }

                MediaLibraryIds.PLAYLISTS -> return collectionFuture(
                    params
                ) { playlistsStateFlow().value.map { it.toBrowsableMediaItem() } }

                MediaLibraryIds.LATEST_ALBUMS -> return collectionFuture(
                    params
                ) { latestAlbumsStateFlow().value.map { it.toBrowsableMediaItem() } }
            }

            if (parentId.startsWith(MediaLibraryIds.ALBUM_PREFIX)) {
                val albumId = parentId.removePrefix(MediaLibraryIds.ALBUM_PREFIX)
                return serviceScope.future(Dispatchers.IO) {
                    val songs = musicFetcher.getSongsFromAlbum(albumId).first()
                    LibraryResult.ofItemList(
                        ImmutableList.copyOf(songs.map { it.toPlayableMediaItem() }),
                        params
                    )
                }
            }
            if (parentId.startsWith(MediaLibraryIds.PLAYLIST_PREFIX)) {
                val playlistId = parentId.removePrefix(MediaLibraryIds.PLAYLIST_PREFIX)
                return serviceScope.future(Dispatchers.IO) {
                    val songs = musicFetcher.getSongsFromPlaylist(playlistId).first()
                    LibraryResult.ofItemList(
                        ImmutableList.copyOf(songs.map { it.toPlayableMediaItem() }),
                        params
                    )
                }
            }

            return Futures.immediateFuture(
                LibraryResult.ofItemList(ImmutableList.of(), params)
            )
        }
    }

    private fun collectionFuture(
        params: LibraryParams?,
        block: suspend () -> List<MediaItem>
    ): ListenableFuture<LibraryResult<ImmutableList<MediaItem>>> =
        serviceScope.future(Dispatchers.IO) {
            val items = block()
            LibraryResult.ofItemList(ImmutableList.copyOf(items), params)
        }

    private fun folderNode(mediaId: String, title: String): MediaItem =
        MediaItem.Builder()
            .setMediaId(mediaId)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(title)
                    .setIsBrowsable(true)
                    .setIsPlayable(false)
                    .setMediaType(MediaMetadata.MEDIA_TYPE_FOLDER_MIXED)
                    .build()
            )
            .build()

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()
        librarySession = MediaLibrarySession.Builder(this, player, libraryCallback)
            .setId("PowerAmpache2LibrarySession")
            .build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaLibrarySession? =
        librarySession

    override fun onDestroy() {
        librarySession.release()
        player.release()
        serviceJob.cancel()
        super.onDestroy()
    }
}
