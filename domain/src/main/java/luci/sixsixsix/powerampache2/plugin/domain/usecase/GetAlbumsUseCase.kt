package luci.sixsixsix.powerampache2.plugin.domain.usecase

import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(private val musicFetcher: MusicFetcher) {
    operator fun invoke(query: String = "") = musicFetcher.getAlbums(query)
}
