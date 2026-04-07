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

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.model.Album
import luci.sixsixsix.powerampache2.plugin.domain.model.Artist
import luci.sixsixsix.powerampache2.plugin.domain.model.Playlist
import luci.sixsixsix.powerampache2.plugin.domain.model.Song
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.emptyList

@Singleton
class MusicFetcherImpl @Inject constructor(): MusicFetcher {
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

    override fun getSongsFromAlbum(albumId: String): Flow<List<Song>> {
        return albumSongsMapFlow
            .map { it[albumId] ?: emptyList() }
            .distinctUntilChanged()
    }

    override fun getSongsFromPlaylist(playlistId: String): Flow<List<Song>> {
        println("aaaa getSongsFromPlaylist $playlistId")
        return playlistSongsMapFlow
            .map { it[playlistId] ?: emptyList() }
            .distinctUntilChanged()
    }

    override fun getAlbumsFromArtist(playlistId: String): List<Album> {
        TODO("Not yet implemented")
    }
}
