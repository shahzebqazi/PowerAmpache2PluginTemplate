package luci.sixsixsix.powerampache2.plugin.androidauto

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import luci.sixsixsix.powerampache2.plugin.domain.model.Album
import luci.sixsixsix.powerampache2.plugin.domain.model.Playlist
import luci.sixsixsix.powerampache2.plugin.domain.model.Song

private fun String?.toArtworkUriOrNull(): Uri? {
    val s = this?.trim().orEmpty()
    if (s.isEmpty()) return null
    return runCatching { Uri.parse(s) }.getOrNull()
}

fun Album.toBrowsableMediaItem(): MediaItem {
    val artUri = artUrl.toArtworkUriOrNull()
    val meta = MediaMetadata.Builder()
        .setTitle(name)
        .setArtist(artist.name)
        .setIsBrowsable(true)
        .setIsPlayable(false)
        .setMediaType(MediaMetadata.MEDIA_TYPE_ALBUM)
        .apply {
            artUri?.let { setArtworkUri(it) }
        }
        .build()
    return MediaItem.Builder()
        .setMediaId("${MediaLibraryIds.ALBUM_PREFIX}$id")
        .setMediaMetadata(meta)
        .build()
}

fun Playlist.toBrowsableMediaItem(): MediaItem {
    val artUri = artUrl?.toArtworkUriOrNull()
    val meta = MediaMetadata.Builder()
        .setTitle(name)
        .setIsBrowsable(true)
        .setIsPlayable(false)
        .setMediaType(MediaMetadata.MEDIA_TYPE_PLAYLIST)
        .apply {
            artUri?.let { setArtworkUri(it) }
        }
        .build()
    return MediaItem.Builder()
        .setMediaId("${MediaLibraryIds.PLAYLIST_PREFIX}$id")
        .setMediaMetadata(meta)
        .build()
}

fun Song.toPlayableMediaItem(): MediaItem {
    val stream = songUrl.trim()
    val artUri = imageUrl.toArtworkUriOrNull()
    val albumTitle = album.name
    val artistName = artist.name
    val meta = MediaMetadata.Builder()
        .setTitle(title)
        .setArtist(artistName)
        .setAlbumTitle(albumTitle)
        .setIsBrowsable(false)
        .setIsPlayable(true)
        .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
        .apply {
            artUri?.let { setArtworkUri(it) }
        }
        .build()
    return MediaItem.Builder()
        .setMediaId(mediaId)
        .setUri(stream)
        .setMediaMetadata(meta)
        .build()
}
