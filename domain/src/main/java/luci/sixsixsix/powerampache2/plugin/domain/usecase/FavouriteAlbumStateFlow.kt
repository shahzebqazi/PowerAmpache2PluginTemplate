package luci.sixsixsix.powerampache2.plugin.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.model.Album
import javax.inject.Inject

class FavouriteAlbumStateFlow @Inject constructor(private val musicFetcher: MusicFetcher) {
    operator fun invoke(): StateFlow<List<Album>> = musicFetcher.favouriteAlbumsFlow
}
