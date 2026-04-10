package luci.sixsixsix.powerampache2.plugin.domain.usecase

import kotlinx.coroutines.flow.first
import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import luci.sixsixsix.powerampache2.plugin.domain.model.Song
import luci.sixsixsix.powerampache2.plugin.domain.model.mocks.SongsMock
import javax.inject.Inject

class GetSongsFromPlaylistUseCase @Inject constructor(private val musicFetcher: MusicFetcher) {
    suspend operator fun invoke(playlistId: String, useMock: Boolean = false): List<Song> =
        if (!useMock) musicFetcher.getSongsFromPlaylist(playlistId).first()
        else SongsMock.getRandomSongList(41)
}
