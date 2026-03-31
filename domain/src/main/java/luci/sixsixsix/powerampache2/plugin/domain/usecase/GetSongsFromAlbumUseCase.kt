package luci.sixsixsix.powerampache2.plugin.domain.usecase

import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import javax.inject.Inject

class GetSongsFromAlbumUseCase @Inject constructor(private val musicFetcher: MusicFetcher) {
    suspend operator fun invoke(albumId: String)= musicFetcher.getSongsFromAlbum(albumId)
}
