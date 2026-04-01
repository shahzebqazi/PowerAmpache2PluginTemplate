package luci.sixsixsix.powerampache2.plugin.domain.usecase

import luci.sixsixsix.powerampache2.plugin.domain.MusicFetcher
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val musicFetcher: MusicFetcher) {
    suspend operator fun invoke(useMock: Boolean = false) =
        if (!useMock)
            "todo"
        else
            "hardcode your token here"
}
