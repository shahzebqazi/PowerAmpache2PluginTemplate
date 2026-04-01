package luci.sixsixsix.powerampache2.plugin.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.model.mocks.SongsMock
import javax.inject.Inject

class GetSongsFromAlbumUseCase @Inject constructor(private val musicFetcher: MusicFetcher) {
    suspend operator fun invoke(albumId: String, useMock: Boolean = false) =
        if (!useMock)
            musicFetcher.getSongsFromAlbum(albumId)
        else
            MutableStateFlow(SongsMock.getRandomSongList(12))
}
