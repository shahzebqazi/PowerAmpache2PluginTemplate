package luci.sixsixsix.powerampache2.plugin.domain.model.mocks

import luci.sixsixsix.powerampache2.plugin.domain.model.Playlist
import luci.sixsixsix.powerampache2.plugin.domain.model.PlaylistType
import kotlin.random.Random

class PlaylistsMock {
    companion object {
        fun getRandomAlbumList(size: Int): List<Playlist> {
            // Ensure that the requested size doesn't exceed the available artists
            val actualSize = size.coerceAtMost(playlists.size)
            return playlists.shuffled(Random).take(actualSize)
        }

        val playlists: List<Playlist> = listOf(
            Playlist(
                id = "2",
                name = "2023-techdeath",
                owner = "System",
                items = 7,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=2&object_type=playlist&id=3148&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "78",
                name = "2024 April New Releaes",
                owner = "admin",
                items = 50,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=78&object_type=playlist&id=10356&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "82",
                name = "2024 August New Releaes",
                owner = "admin",
                items = 178,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=82&object_type=playlist&id=10441&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "90",
                name = "2024 December New Releases",
                owner = "luci",
                items = 100,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=90&object_type=playlist&id=11458&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "76",
                name = "2024 February New Releases",
                owner = "admin",
                items = 70,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=76&object_type=playlist&id=10317&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "75",
                name = "2024 January New Releases ",
                owner = "admin",
                items = 97,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=75&object_type=playlist&id=10300&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "81",
                name = "2024 July New Releaes",
                owner = "admin",
                items = 80,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=81&object_type=playlist&id=10414&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "80",
                name = "2024 June New Releaes",
                owner = "admin",
                items = 96,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=80&object_type=playlist&id=10391&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "77",
                name = "2024 March New Releaes",
                owner = "admin",
                items = 133,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=77&object_type=playlist&id=10347&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "79",
                name = "2024 May New Releaes",
                owner = "admin",
                items = 98,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=79&object_type=playlist&id=10373&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "85",
                name = "2024 November New Releases",
                owner = "admin",
                items = 119,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=85&object_type=playlist&id=11036&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "83",
                name = "2024 October New Releases",
                owner = "admin",
                items = 149,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=83&object_type=playlist&id=10522&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "74",
                name = "2024 September New Releases  ",
                owner = "admin",
                items = 114,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=74&object_type=playlist&id=10200&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "100",
                name = "2025 April",
                owner = "luci",
                items = 161,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=100&object_type=playlist&id=12751&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "106",
                name = "2025 August",
                owner = "luci",
                items = 147,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=106&object_type=playlist&id=14325&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 4.5f,
                preciseRating = 0f
            ),
            Playlist(
                id = "121",
                name = "2025 December",
                owner = "admin",
                items = 119,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=121&object_type=playlist&id=14872&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 5f,
                preciseRating = 0f
            ),
            Playlist(
                id = "96",
                name = "2025 February",
                owner = "luci",
                items = 110,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=96&object_type=playlist&id=12281&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "92",
                name = "2025 January",
                owner = "luci",
                items = 210,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=92&object_type=playlist&id=12285&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "105",
                name = "2025 July",
                owner = "admin",
                items = 104,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=105&object_type=playlist&id=14058&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f
            ),
            Playlist(
                id = "102",
                name = "2025 June",
                owner = "admin",
                items = 125,
                type = PlaylistType.public,
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=102&object_type=playlist&id=13025&name=art.jpg",
                flag = 0,
                rating = 0,
                averageRating = 0.0f,
                preciseRating = 0f

            )
        )
    }
}