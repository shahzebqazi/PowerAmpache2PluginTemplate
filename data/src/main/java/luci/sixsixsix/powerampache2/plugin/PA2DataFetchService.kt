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
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import luci.sixsixsix.powerampache2.plugin.data.dto.AlbumsDto
import luci.sixsixsix.powerampache2.plugin.data.dto.ArtistsDto
import luci.sixsixsix.powerampache2.plugin.data.dto.PlaylistsDto
import luci.sixsixsix.powerampache2.plugin.data.dto.SongsDto
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_ALBUMS
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_ARTISTS
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_GET_SONGS_ALBUM
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_GET_SONGS_PLAYLIST
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_PLAYLISTS
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_SONGS_ALBUM
import luci.sixsixsix.powerampache2.plugin.domain.common.ACTION_SONGS_PLAYLIST

import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_ACTION
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_ALBUM_ID
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_ID
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_REQUEST_JSON
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_RESPONSE_SUCCESS
import luci.sixsixsix.powerampache2.plugin.domain.common.MSG_DATA
import luci.sixsixsix.powerampache2.plugin.domain.model.Song
import luci.sixsixsix.powerampache2.plugin.domain.model.mocks.SongsMock.Companion.songs
import javax.inject.Inject

@AndroidEntryPoint
class PA2DataFetchService : Service() {
    @Inject lateinit var musicFetcher: MusicFetcher
    @Inject lateinit var applicationCoroutineScope: CoroutineScope
    private val gson = Gson()
    // Store client's messenger for bidirectional communication
    private var clientMessenger: Messenger? = null


    private val messenger = Messenger(object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val action = msg.data.getString(KEY_ACTION) ?: return

            when (action) {
                "register_client" -> {
                    // ONLY store clientMessenger here
                    clientMessenger = msg.replyTo
                }
                else -> {
                    parseJsonString(
                        action,
                        jsonStr = msg.data.getString(KEY_REQUEST_JSON) ?: return,
                        albumId = msg.data.getString(KEY_ID)
                    )
                    val replyTo = msg.replyTo ?: return
                    replyTo.send(Message.obtain().apply { data = Bundle().apply { putBoolean(KEY_RESPONSE_SUCCESS, true) } })
                }
            }
        }
    })

    /**
     * Request songs for a specific album from the client
     */
    fun requestSongsForAlbum(albumId: String) {
        clientMessenger?.let { messenger ->
            val msg = Message.obtain().apply {
                data = Bundle().apply {
                    putString(KEY_ACTION, ACTION_GET_SONGS_ALBUM)
                    putString(KEY_ALBUM_ID, albumId)
                }
                //println("aaaa requestSongsForAlbum id: ${albumId}")
            }
            try {
                messenger.send(msg)
            } catch (e: RemoteException) {
                clientMessenger = null
            }
        }
    }

    fun requestSongsForPlaylist(playlistId: String) {
        clientMessenger?.let { messenger ->
            val msg = Message.obtain().apply {
                data = Bundle().apply {
                    putString(KEY_ACTION, ACTION_GET_SONGS_PLAYLIST)
                    putString(KEY_ALBUM_ID, playlistId)
                }
                //println("aaaa requestSongsForAlbum id: ${albumId}")
            }
            try {
                messenger.send(msg)
            } catch (e: RemoteException) {
                clientMessenger = null
            }
        }
    }


    private fun parseJsonString(action: String, jsonStr: String, albumId: String? = null) {
        println("aaaa parseJsonString $action")
        when(action) {
            ACTION_PLAYLISTS -> gson.fromJson(jsonStr, PlaylistsDto::class.java).playlists.also {
                musicFetcher.playlistsFlow.value = it
            }
            ACTION_ARTISTS -> gson.fromJson(jsonStr, ArtistsDto::class.java).artists.also {
                musicFetcher.artistsFlow.value = it
            }
            ACTION_ALBUMS -> gson.fromJson(jsonStr, AlbumsDto::class.java).albums.also {
                musicFetcher.albumsFlow.value = it
            }
            ACTION_SONGS_ALBUM -> gson.fromJson(jsonStr, SongsDto::class.java).songs.also { songs ->
                //println("aaaa ACTION_SONGS_RESPONSE ${albumId}")
                musicFetcher.albumSongsMapFlow.update { map ->
                    (map + (albumId!! to songs))
                }
            }
            ACTION_SONGS_PLAYLIST -> gson.fromJson(jsonStr, SongsDto::class.java).songs.also { songs ->
                println("aaaa ACTION_SONGS_PLAYLIST ${albumId}")
                musicFetcher.playlistSongsMapFlow.update { map ->
                    (map + (albumId!! to songs))
                }
            }
            "highest_albums" -> gson.fromJson(jsonStr, AlbumsDto::class.java).albums.also { albums ->
                musicFetcher.highRatedAlbumsFlow.value = albums
                for(album in albums) {
                    requestSongsForAlbum(album.id)
                }
            }
            "favourite_albums" -> gson.fromJson(jsonStr, AlbumsDto::class.java).albums.also { albums ->
                musicFetcher.favouriteAlbumsFlow.value = albums
                for(album in albums) {
                    requestSongsForAlbum(album.id)
                }
            }
            "recent_albums" -> gson.fromJson(jsonStr, AlbumsDto::class.java).albums.also { albums ->
                musicFetcher.recentAlbumsFlow.value = albums
                for(album in albums) {
                    requestSongsForAlbum(album.id)
                }
            }
            "latest_albums" -> gson.fromJson(jsonStr, AlbumsDto::class.java).albums.also { albums ->
                musicFetcher.latestAlbumsFlow.value = albums
                for(album in albums) {
                    requestSongsForAlbum(album.id)
                }
            }
            "queue" -> gson.fromJson(jsonStr, SongsDto::class.java).songs.also {
                musicFetcher.currentQueueFlow.value = it
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return messenger.binder
    }

    private fun sendResponse(replyTo: Messenger?, data: Map<String, Any>) {
        replyTo?.let { messenger ->
            val msg = Message.obtain().apply {
                this.data = Bundle().apply {
                    data.forEach { (key, value) ->
                        when (value) {
                            is Boolean -> putBoolean(key, value)
                            is String -> putString(key, value)
                            is Int -> putInt(key, value)
                        }
                    }
                }
            }
            try {
                messenger.send(msg)
            } catch (e: RemoteException) {
                // Handle disconnected client
            }
        }
    }
}
