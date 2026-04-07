package luci.sixsixsix.powerampache2.plugin.data.dto

import com.google.gson.annotations.SerializedName
import luci.sixsixsix.powerampache2.plugin.domain.model.Artist

data class ArtistsDto(
    @SerializedName("data")
    val artists: List<Artist> = listOf()
)
