package luci.sixsixsix.powerampache2.plugin.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.model.mocks.AlbumsMock
import javax.inject.Inject

class GetAlbumsFromArtistUseCase @Inject constructor(private val musicFetcher: MusicFetcher) {
    suspend operator fun invoke(artistId: String, useMock: Boolean = false) =
        if (!useMock)
            musicFetcher.getAlbumsFromArtist(artistId)
        else
            MutableStateFlow(AlbumsMock.albums)
}
