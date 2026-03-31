package luci.sixsixsix.powerampache2.plugin.domain.usecase

import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import javax.inject.Inject

class GetAlbumsFromArtistUseCase @Inject constructor(private val musicFetcher: MusicFetcher) {
    suspend operator fun invoke(artistId: String)= musicFetcher.getAlbumsFromArtist(artistId)
}
