package luci.sixsixsix.powerampache2.plugin.data.dto

import com.google.gson.annotations.SerializedName
import luci.sixsixsix.powerampache2.plugin.domain.model.Playlist


data class PlaylistsDto(
    @SerializedName("playlists")
    val playlists: List<Playlist> = listOf()
)
