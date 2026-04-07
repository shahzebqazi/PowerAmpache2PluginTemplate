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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import luci.sixsixsix.powerampache2.plugin.domain.usecase.GetSongsFromAlbumUseCase
import luci.sixsixsix.powerampache2.plugin.domain.usecase.HighestAlbumsStateFlow
import luci.sixsixsix.powerampache2.plugin.domain.usecase.QueueStateFlow
import javax.inject.Inject

@HiltViewModel
class SongListViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    queueStateFlowUseCase: QueueStateFlow,
    highestAlbumsStateFlowUseCase: HighestAlbumsStateFlow,
    getSongsFromAlbumUseCase: GetSongsFromAlbumUseCase
) : ViewModel() {
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
}
