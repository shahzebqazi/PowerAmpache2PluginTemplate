package luci.sixsixsix.powerampache2.plugin.domain.usecase

import kotlinx.coroutines.flow.first
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.model.Song
import luci.sixsixsix.powerampache2.plugin.domain.model.mocks.SongsMock
import javax.inject.Inject

class GetSongsFromAlbumUseCase @Inject constructor(private val musicFetcher: MusicFetcher) {
    suspend operator fun invoke(albumId: String, useMock: Boolean = false): List<Song> =
        if (!useMock) musicFetcher.getSongsFromAlbum(albumId).first()
        else SongsMock.getRandomSongList(12)
}
