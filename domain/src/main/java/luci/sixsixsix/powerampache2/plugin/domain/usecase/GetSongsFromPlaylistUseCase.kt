package luci.sixsixsix.powerampache2.plugin.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.model.mocks.SongsMock
import javax.inject.Inject


class GetSongsFromPlaylistUseCase @Inject constructor(private val musicFetcher: MusicFetcher) {
    suspend operator fun invoke(playlistId: String, useMock: Boolean = false) =
        if (!useMock)
            musicFetcher.getSongsFromPlaylist(playlistId)
        else
            MutableStateFlow(SongsMock.getRandomSongList(41))
}
