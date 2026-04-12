/**
 * Copyright (C) 2025  Antonio Tari
 *
 * This file is a part of Power Ampache 2
 * Ampache Android client application
 * @author Antonio Tari
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package luci.sixsixsix.powerampache2.plugin

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.Process
import android.os.RemoteException
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update
import luci.sixsixsix.powerampache2.plugin.data.dto.AlbumsDto
import luci.sixsixsix.powerampache2.plugin.data.dto.ArtistsDto
import luci.sixsixsix.powerampache2.plugin.data.dto.PlaylistsDto
import luci.sixsixsix.powerampache2.plugin.data.dto.SongsDto
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcherListener
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_ALBUMS
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_ARTISTS
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_GET_ALBUMS
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_GET_ALBUMS_ARTIST
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_GET_ARTISTS
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_GET_SONGS_ALBUM
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_GET_SONGS_PLAYLIST
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_PLAYLISTS
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_SONGS_ALBUM
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_SONGS_PLAYLIST
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_ACTION
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_ALBUM_ID
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_ID
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_PLAYLIST_ID
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_QUERY
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_REQUEST_JSON
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_RESPONSE_SUCCESS
import luci.sixsixsix.powerampache2.plugin.domain.model.Album
import luci.sixsixsix.powerampache2.plugin.domain.model.Artist
import luci.sixsixsix.powerampache2.plugin.domain.model.Song
import javax.inject.Inject

@AndroidEntryPoint
class PA2DataFetchService : Service(), MusicFetcherListener {
    @Inject lateinit var musicFetcher: MusicFetcher
    private val gson = Gson()
    private var clientMessenger: Messenger? = null

    private val messenger = Messenger(object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (!isTrustedSender(msg)) {
                Log.w(TAG, "ignored IPC from untrusted uid (sendingUid=${msg.sendingUid})")
                return
            }
            val action = msg.data.getString(KEY_ACTION) ?: return

            when (action) {
                "register_client" -> {
                    clientMessenger = msg.replyTo
                }
                else -> {
                    val jsonStr = msg.data.getString(KEY_REQUEST_JSON) ?: return
                    val id = resolveBundleId(msg)
                    parseJsonString(action, jsonStr, id)
                    val replyTo = msg.replyTo ?: return
                    try {
                        replyTo.send(
                            Message.obtain().apply {
                                data = Bundle().apply { putBoolean(KEY_RESPONSE_SUCCESS, true) }
                            }
                        )
                    } catch (e: RemoteException) {
                        Log.w(TAG, "reply send failed", e)
                    }
                }
            }
        }
    })

    /**
     * Only accept IPC from the Power Ampache host family when [Message.sendingUid] is populated.
     * [Message.sendingUid] is often [Process.INVALID_UID] for Messenger-delivered messages; treating
     * that as unknown (allowed) restores host→plugin JSON delivery. When UID is set, require a
     * non-plugin package under the Power Ampache namespace.
     */
    private fun isTrustedSender(msg: Message): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return true
        }
        val uid = msg.sendingUid
        if (uid == Process.INVALID_UID) {
            return true
        }
        val names = packageManager.getPackagesForUid(uid) ?: return false
        val self = packageName
        return names.any { pkg ->
            pkg != self && pkg.startsWith(HOST_PACKAGE_PREFIX)
        }
    }

    /** Hosts may put the entity id under [KEY_ID] or alternate keys. */
    private fun resolveBundleId(msg: Message): String? {
        val d = msg.data
        return d.getString(KEY_ID)
            ?: d.getString(KEY_PLAYLIST_ID)
            ?: d.getString(KEY_ALBUM_ID)
            ?: d.getString(BUNDLE_KEY_PLAYLIST_ID_ALT)
            ?: d.getString(BUNDLE_KEY_ALBUM_ID_ALT)
    }

    override fun onCreate() {
        super.onCreate()
        musicFetcher.musicFetcherListener = this
    }

    override fun onDestroy() {
        musicFetcher.musicFetcherListener = null
        super.onDestroy()
    }

    fun requestArtists(query: String = "") {
        clientMessenger?.let { messenger ->
            val msg = Message.obtain().apply {
                data = Bundle().apply {
                    putString(KEY_ACTION, ACTION_GET_ARTISTS)
                    putString(KEY_QUERY, query)
                }
            }
            try {
                messenger.send(msg)
            } catch (e: RemoteException) {
                clientMessenger = null
            }
        }
    }

    fun requestAlbums(query: String = "") {
        clientMessenger?.let { messenger ->
            val msg = Message.obtain().apply {
                data = Bundle().apply {
                    putString(KEY_ACTION, ACTION_GET_ALBUMS)
                    putString(KEY_QUERY, query)
                }
            }
            try {
                messenger.send(msg)
            } catch (e: RemoteException) {
                clientMessenger = null
            }
        }
    }

    fun requestSongsForAlbum(albumId: String) {
        val messenger = clientMessenger
        if (messenger == null) {
            Log.w(TAG, "requestSongsForAlbum: host not registered; albumId=$albumId")
            return
        }
        val msg = Message.obtain().apply {
            data = Bundle().apply {
                putString(KEY_ACTION, ACTION_GET_SONGS_ALBUM)
                putString(KEY_ALBUM_ID, albumId)
            }
        }
        try {
            messenger.send(msg)
        } catch (e: RemoteException) {
            clientMessenger = null
        }
    }

    fun requestAlbumsFromArtist(artistId: String) {
        clientMessenger?.let { messenger ->
            val msg = Message.obtain().apply {
                data = Bundle().apply {
                    putString(KEY_ACTION, ACTION_GET_ALBUMS_ARTIST)
                    putString(KEY_ID, artistId)
                }
            }
            try {
                messenger.send(msg)
            } catch (e: RemoteException) {
                clientMessenger = null
            }
        }
    }

    fun requestSongsForPlaylist(playlistId: String) {
        val messenger = clientMessenger
        if (messenger == null) {
            Log.w(TAG, "requestSongsForPlaylist: host not registered (no clientMessenger); id=$playlistId")
            return
        }
        val msg = Message.obtain().apply {
            data = Bundle().apply {
                putString(KEY_ACTION, ACTION_GET_SONGS_PLAYLIST)
                putString(KEY_PLAYLIST_ID, playlistId)
            }
        }
        try {
            messenger.send(msg)
        } catch (e: RemoteException) {
            clientMessenger = null
        }
    }

    private fun parseJsonString(action: String, jsonStr: String, albumId: String? = null) {
        try {
            when (action) {
                ACTION_PLAYLISTS -> {
                    val playlists = gson.fromJson(jsonStr, PlaylistsDto::class.java).playlists
                    musicFetcher.playlistsFlow.value = playlists
                }
                ACTION_ARTISTS -> {
                    val artists = gson.fromJson(jsonStr, ArtistsDto::class.java).artists
                    musicFetcher.artistsFlow.update { old -> mergeArtistsById(old, artists) }
                }
                ACTION_ALBUMS -> {
                    val albums = gson.fromJson(jsonStr, AlbumsDto::class.java).albums
                    musicFetcher.albumsFlow.update { old -> mergeAlbumsById(old, albums) }
                    if (albumId != null) {
                        musicFetcher.albumsByArtistFlow.update { it + (albumId to albums) }
                    }
                }
                ACTION_SONGS_ALBUM -> {
                    val songs = gson.fromJson(jsonStr, SongsDto::class.java).songs
                    val id = albumId ?: songs.firstOrNull()?.album?.id?.takeIf { it.isNotBlank() }
                    if (id == null) {
                        Log.w(TAG, "ACTION_SONGS_ALBUM missing id (bundle + song album); song count=${songs.size}")
                        return
                    }
                    musicFetcher.albumSongsMapFlow.update { map -> map + (id to songs) }
                }
                ACTION_SONGS_PLAYLIST -> {
                    val songs = gson.fromJson(jsonStr, SongsDto::class.java).songs
                    val id = albumId
                        ?: parseTopLevelStringField(jsonStr, "playlist_id", "playlistId")
                    if (id == null) {
                        Log.w(TAG, "ACTION_SONGS_PLAYLIST missing id (bundle + JSON); song count=${songs.size}")
                        return
                    }
                    musicFetcher.playlistSongsMapFlow.update { map ->
                        val newList = LinkedHashSet(map[id] ?: emptyList())
                        newList.addAll(songs)
                        map + (id to newList.toList())
                    }
                }
                "highest_albums" -> {
                    val albums = gson.fromJson(jsonStr, AlbumsDto::class.java).albums
                    addToAlbumsList(albums)
                    musicFetcher.highRatedAlbumsFlow.update { old -> mergeAlbumsById(old, albums) }
                }
                "favourite_albums" -> {
                    val albums = gson.fromJson(jsonStr, AlbumsDto::class.java).albums
                    addToAlbumsList(albums)
                    musicFetcher.favouriteAlbumsFlow.update { old -> mergeAlbumsById(old, albums) }
                }
                "recent_albums" -> {
                    val albums = gson.fromJson(jsonStr, AlbumsDto::class.java).albums
                    addToAlbumsList(albums)
                    musicFetcher.recentAlbumsFlow.update { old -> mergeAlbumsById(old, albums) }
                }
                "latest_albums" -> {
                    val albums = gson.fromJson(jsonStr, AlbumsDto::class.java).albums
                    addToAlbumsList(albums)
                    musicFetcher.latestAlbumsFlow.update { old -> mergeAlbumsById(old, albums) }
                }
                "queue" -> {
                    val songs = gson.fromJson(jsonStr, SongsDto::class.java).songs
                    musicFetcher.currentQueueFlow.value = songs
                }
            }
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, "JSON parse failed action=$action", e)
        } catch (e: RuntimeException) {
            Log.e(TAG, "parse failed action=$action", e)
        }
    }

    private fun addToAlbumsList(albums: List<Album>) {
        musicFetcher.albumsFlow.update { oldAlbums -> mergeAlbumsById(oldAlbums, albums) }
    }

    override fun onBind(intent: Intent?): IBinder {
        return messenger.binder
    }

    override fun getAlbums(query: String) = requestAlbums(query)
    override fun getArtists(query: String) = requestArtists(query)
    override fun getSongsFromAlbum(albumId: String) = requestSongsForAlbum(albumId)
    override fun getSongsFromPlaylist(playlistId: String) = requestSongsForPlaylist(playlistId)
    override fun getAlbumsFromArtist(artistId: String) = requestAlbumsFromArtist(artistId)

    private fun parseTopLevelStringField(jsonStr: String, vararg keys: String): String? =
        runCatching {
            val obj = JsonParser.parseString(jsonStr).asJsonObject
            for (k in keys) {
                if (!obj.has(k)) continue
                val el = obj.get(k) ?: continue
                if (el.isJsonPrimitive && el.asJsonPrimitive.isString) {
                    return el.asString
                }
            }
            null
        }.getOrNull()

    private companion object {
        private const val TAG = "PA2DataFetch"
        private const val HOST_PACKAGE_PREFIX = "luci.sixsixsix.powerampache2"
        private const val BUNDLE_KEY_PLAYLIST_ID_ALT = "playlist_id"
        private const val BUNDLE_KEY_ALBUM_ID_ALT = "album_id"

        private fun mergeArtistsById(old: List<Artist>, new: List<Artist>): List<Artist> =
            (old + new).groupBy { it.id }.map { (_, list) -> list.last() }

        private fun mergeAlbumsById(old: List<Album>, new: List<Album>): List<Album> =
            (old + new).groupBy { it.id }.map { (_, list) -> list.last() }
    }
}
