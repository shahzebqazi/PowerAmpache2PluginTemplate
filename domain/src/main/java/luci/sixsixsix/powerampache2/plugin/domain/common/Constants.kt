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
package luci.sixsixsix.powerampache2.plugin.domain.common

const val defaultContentType: String = "audio/mp3"
const val KEY_REQUEST_JSON = "json"
const val KEY_RESPONSE_SUCCESS = "success"
const val KEY_ACTION = "action"
const val ERROR_INT = -1
const val KEY_ID = "id"
const val NOT_IMPLEMENTED_USER_ID = "666"
const val ERROR_FLOAT = ERROR_INT.toFloat()
const val ERROR_STRING = "ERROR"
const val LOADING_STRING = "LOADING"
const val USER_ID_ERROR = ERROR_INT
const val DEFAULT_NO_IMAGE = ""

// Message.what codes (MUST be Int)
const val MSG_REGISTER_CLIENT = 1
const val MSG_DATA = 2
const val MSG_REQUEST_SONGS = 3

const val KEY_ALBUM_ID = KEY_ID
const val KEY_PLAYLIST_ID = KEY_ID

// Actions the service can request from client
const val ACTION_GET_SONGS_ALBUM = "get_songs_album"
const val ACTION_GET_SONGS_PLAYLIST = "get_songs_playlist"

// Actions client sends to service
const val ACTION_PLAYLISTS = "playlists"
const val ACTION_ARTISTS = "artists"
const val ACTION_ALBUMS = "albums"
const val ACTION_SONGS_RESPONSE = "songs_response"
const val ACTION_SONGS_ALBUM = "songs_album"
const val ACTION_SONGS_PLAYLIST= "songs_playlist"
