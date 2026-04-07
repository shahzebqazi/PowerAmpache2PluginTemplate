package luci.sixsixsix.powerampache2.plugin.data.dto

import com.google.gson.annotations.SerializedName
import luci.sixsixsix.powerampache2.plugin.domain.model.Album

data class AlbumsDto(
    @SerializedName("data")
    val albums: List<Album> = listOf()
)
