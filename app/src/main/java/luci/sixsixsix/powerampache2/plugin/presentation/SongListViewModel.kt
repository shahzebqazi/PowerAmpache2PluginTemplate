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
package luci.sixsixsix.powerampache2.plugin.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import luci.sixsixsix.powerampache2.plugin.domain.model.Song
import luci.sixsixsix.powerampache2.plugin.domain.usecase.GetAlbumsUseCase
import luci.sixsixsix.powerampache2.plugin.domain.usecase.GetArtistsUseCase
import luci.sixsixsix.powerampache2.plugin.domain.usecase.GetSongsFromAlbumUseCase
import luci.sixsixsix.powerampache2.plugin.domain.usecase.GetSongsFromPlaylistUseCase
import luci.sixsixsix.powerampache2.plugin.domain.usecase.HighestAlbumsStateFlow
import luci.sixsixsix.powerampache2.plugin.domain.usecase.PlaylistsStateFlow
import luci.sixsixsix.powerampache2.plugin.domain.usecase.QueueStateFlow
import javax.inject.Inject

@HiltViewModel
class SongListViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    queueStateFlowUseCase: QueueStateFlow,
    private val highestAlbumsStateFlowUseCase: HighestAlbumsStateFlow,
    private val getArtistsUseCase: GetArtistsUseCase,
    private val playlistsStateFlow: PlaylistsStateFlow,
    private val getSongsFromPlaylistUseCase: GetSongsFromPlaylistUseCase,
    private val getSongsFromAlbumUseCase: GetSongsFromAlbumUseCase,
    private val albumStateFlow: GetAlbumsUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            delay(5000)
            getArtistsUseCase().collect { artists ->
                println("aaaa SongListViewModel artists ${artists.size}")
            }
        }

        viewModelScope.launch {
            delay(5000)

            highestAlbums()
        }

        viewModelScope.launch {
            delay(5000)

            albumStateFlow().collect { albums ->
                getSongsFromAlbumUseCase(albums[albums.size-1].id).collect { songs ->
                    println("aaaa SongListViewModel songs from album ${songs.size}    id: ${albums[0].id}")
                }
            }
        }

        viewModelScope.launch {
            delay(5000)

            playlistsStateFlow().filter { it.isNotEmpty() }.collect { playlists ->
                getSongsFromPlaylistUseCase(playlists[0].id).filter { it.isNotEmpty() }.collect { songs ->
                    println("aaaa SongListViewModel songs from playlist ${songs.size}    id: ${playlists[0].id}  ${songs[0].songUrl}")
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    val queueStateFlow = queueStateFlowUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, listOf())

    val highestAlbumsStateFlow = highestAlbumsStateFlowUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, listOf())

    val singleAlbumStateFlow =
        highestAlbumsStateFlowUseCase()
            .flatMapConcat { getSongsFromAlbumUseCase("2011") }
            .stateIn(viewModelScope, SharingStarted.Eagerly, listOf())


    fun highestAlbums() {
        viewModelScope.launch {
            highestAlbumsStateFlow.filter { it.isNotEmpty() }.collect { albums ->
                val firstAlbum = albums[0]
                val songs: List<Song> = getSongsFromAlbumUseCase(firstAlbum.id).filter { it.isNotEmpty() }.first()
                println("aaaa SongListViewModel.highestAlbums songs ${songs.size}")

            }
        }
    }
}
