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
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import luci.sixsixsix.powerampache2.plugin.data.dto.AlbumsDto
import luci.sixsixsix.powerampache2.plugin.data.dto.ArtistsDto
import luci.sixsixsix.powerampache2.plugin.data.dto.PlaylistsDto
import luci.sixsixsix.powerampache2.plugin.data.dto.QueueDto
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_ACTION
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_REQUEST_JSON
import luci.sixsixsix.powerampache2.plugin.domain.common.KEY_RESPONSE_SUCCESS
import javax.inject.Inject

@AndroidEntryPoint
class QueueFetchService : Service() {
    @Inject lateinit var musicFetcher: MusicFetcher
    @Inject lateinit var applicationCoroutineScope: CoroutineScope

    private val gson = Gson()

    private val messenger = Messenger(object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            parseJsonString(
                action = msg.data.getString(KEY_ACTION) ?: return,
                jsonStr = msg.data.getString(KEY_REQUEST_JSON) ?: return
            )

            val replyTo = msg.replyTo ?: return
            replyTo.send(Message.obtain().apply { Bundle().apply { putBoolean(KEY_RESPONSE_SUCCESS, true) } })
        }
    })

    private fun parseJsonString(action: String, jsonStr: String) {
        when(action) {
            "playlists" -> gson.fromJson(jsonStr, PlaylistsDto::class.java).playlists.also {
                musicFetcher.playlistsFlow.value = it
            }
            "artists" -> gson.fromJson(jsonStr, ArtistsDto::class.java).artists.also {
                musicFetcher.artistsFlow.value = it
            }
            "albums" -> gson.fromJson(jsonStr, AlbumsDto::class.java).albums.also {
                musicFetcher.albumsFlow.value = it
            }
            "highest_albums" -> gson.fromJson(jsonStr, AlbumsDto::class.java).albums.also {
                musicFetcher.highRatedAlbumsFlow.value = it
            }
            "favourite_albums" -> gson.fromJson(jsonStr, AlbumsDto::class.java).albums.also {
                musicFetcher.favouriteAlbumsFlow.value = it
            }
            "recent_albums" -> gson.fromJson(jsonStr, AlbumsDto::class.java).albums.also {
                musicFetcher.recentAlbumsFlow.value = it
            }
            "latest_albums" -> gson.fromJson(jsonStr, AlbumsDto::class.java).albums.also {
                musicFetcher.latestAlbumsFlow.value = it
            }
            "queue" -> gson.fromJson(jsonStr, QueueDto::class.java).queue.also {
                musicFetcher.currentQueueFlow.value = it
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return messenger.binder
    }
}
