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
package luci.sixsixsix.powerampache2.plugin.data

import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcherListener
import luci.sixsixsix.powerampache2.plugin.PA2DataFetchService
import luci.sixsixsix.powerampache2.plugin.domain.model.Album
import luci.sixsixsix.powerampache2.plugin.domain.model.Artist
import luci.sixsixsix.powerampache2.plugin.domain.model.Playlist
import luci.sixsixsix.powerampache2.plugin.domain.model.Song
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.emptyList

@Singleton
class MusicFetcherImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
) : MusicFetcher {
    override var musicFetcherListener: MusicFetcherListener? = null
    override val currentQueueFlow = MutableStateFlow<List<Song>>(emptyList())
    override val playlistsFlow = MutableStateFlow<List<Playlist>>(emptyList())
    override val artistsFlow = MutableStateFlow<List<Artist>>(emptyList())
    override val albumsFlow = MutableStateFlow<List<Album>>(emptyList())
    override val favouriteAlbumsFlow = MutableStateFlow<List<Album>>(emptyList())
    override val recentAlbumsFlow = MutableStateFlow<List<Album>>(emptyList())
    override val latestAlbumsFlow = MutableStateFlow<List<Album>>(emptyList())
    override val highRatedAlbumsFlow = MutableStateFlow<List<Album>>(emptyList())
    override val albumSongsMapFlow: MutableStateFlow<Map<String, List<Song>>> = MutableStateFlow(emptyMap())
    override val playlistSongsMapFlow: MutableStateFlow<Map<String, List<Song>>> = MutableStateFlow(emptyMap())
    override val albumsByArtistFlow: MutableStateFlow<Map<String, List<Album>>> = MutableStateFlow(emptyMap())

    override fun getSongsFromAlbum(albumId: String): Flow<List<Song>> {
        val listener = musicFetcherListener
        if (listener == null) {
            Log.w(TAG, "getSongsFromAlbum: no listener (start PA2DataFetchService); albumId=$albumId")
            ensureFetchServiceRunning()
        } else {
            listener.getSongsFromAlbum(albumId)
        }
        return albumSongsMapFlow
            .map { it[albumId] ?: emptyList() }
            .distinctUntilChanged()
    }

    override fun getSongsFromPlaylist(playlistId: String): Flow<List<Song>> {
        val listener = musicFetcherListener
        if (listener == null) {
            Log.w(TAG, "getSongsFromPlaylist: no listener (start PA2DataFetchService); playlistId=$playlistId")
            ensureFetchServiceRunning()
        } else {
            listener.getSongsFromPlaylist(playlistId)
        }
        return playlistSongsMapFlow
            .map { it[playlistId] ?: emptyList() }
            .distinctUntilChanged()
    }

    override fun getArtists(query: String): Flow<List<Artist>> {
        val listener = musicFetcherListener
        if (listener == null) {
            ensureFetchServiceRunning()
        } else {
            listener.getArtists(query)
        }
        return artistsFlow
    }

    override fun getAlbums(query: String): Flow<List<Album>> {
        val listener = musicFetcherListener
        if (listener == null) {
            ensureFetchServiceRunning()
        } else {
            listener.getAlbums(query)
        }
        return albumsFlow
    }

    override fun getAlbumsFromArtist(artistId: String): Flow<List<Album>> {
        val listener = musicFetcherListener
        if (listener == null) {
            ensureFetchServiceRunning()
        } else {
            listener.getAlbumsFromArtist(artistId)
        }
        return combine(albumsByArtistFlow, albumsFlow) { byArtist, all ->
            byArtist[artistId]?.takeIf { it.isNotEmpty() }
                ?: all.filter { it.artist.id == artistId }
        }.distinctUntilChanged()
    }

    /** Best-effort: start [PA2DataFetchService] so it can set [musicFetcherListener]. */
    private fun ensureFetchServiceRunning() {
        if (musicFetcherListener != null) return
        appContext.startService(Intent(appContext, PA2DataFetchService::class.java))
    }

    private companion object {
        private const val TAG = "MusicFetcherImpl"
    }
}
