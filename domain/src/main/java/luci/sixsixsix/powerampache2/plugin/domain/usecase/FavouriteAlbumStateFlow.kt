package luci.sixsixsix.powerampache2.plugin.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.model.Album
import luci.sixsixsix.powerampache2.plugin.domain.model.mocks.AlbumsMock
import javax.inject.Inject

class FavouriteAlbumStateFlow @Inject constructor(private val musicFetcher: MusicFetcher) {
    operator fun invoke(useMock: Boolean = false): StateFlow<List<Album>> =
        if (!useMock)
            musicFetcher.favouriteAlbumsFlow
        else
            MutableStateFlow(AlbumsMock.getRandomAlbumList(14))
}
