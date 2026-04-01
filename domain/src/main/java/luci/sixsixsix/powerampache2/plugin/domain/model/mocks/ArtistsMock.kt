package luci.sixsixsix.powerampache2.plugin.domain.model.mocks

import luci.sixsixsix.powerampache2.plugin.domain.model.Artist
import luci.sixsixsix.powerampache2.plugin.domain.model.MusicAttribute
import kotlin.random.Random

class ArtistsMock {
    companion object {
        // Returns a random list of artists, with the specified size.
        fun getRandomArtistList(size: Int): List<Artist> {
            // Ensure that the requested size doesn't exceed the available artists
            val actualSize = size.coerceAtMost(artists.size)
            return artists.shuffled(Random).take(actualSize)
        }

        val artists: List<Artist> = listOf(
            Artist(
                id = "2520",
                name = ":A7IE:",
                albumCount = 1,
                songCount = 12,
                genre = listOf(
                    MusicAttribute(id = "226", name = "Dark Electro"),
                    MusicAttribute(id = "473", name = "EBM"),
                    MusicAttribute(id = "53", name = "Industrial"),
                    MusicAttribute(id = "474", name = "Darkelectro")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=2520&object_type=artist&id=18700&name=art.jpg",
                flag = 0,
                summary = null,
                time = 11561,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "2521",
                name = ":A7IE:",
                albumCount = 1,
                songCount = 12,
                genre = listOf(
                    MusicAttribute(id = "226", name = "Dark Electro"),
                    MusicAttribute(id = "473", name = "EBM"),
                    MusicAttribute(id = "53", name = "Industrial"),
                    MusicAttribute(id = "474", name = "Darkelectro")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=2521&object_type=artist&id=18699&name=art.jpg",
                flag = 0,
                summary = null,
                time = 3576,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "4321",
                name = ":Wumpscut:",
                albumCount = 0,
                songCount = 2,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "226", name = "Dark Electro"),
                    MusicAttribute(id = "224", name = "Electro-Industrial"),
                    MusicAttribute(id = "473", name = "EBM"),
                    MusicAttribute(id = "512", name = "Futurepop"),
                    MusicAttribute(id = "227", name = "Power Noise"),
                    MusicAttribute(id = "511", name = "Synthpop")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=4321&object_type=artist&id=21228&name=art.jpg",
                flag = 0,
                summary = null,
                time = 1327,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "1859",
                name = "?uestlove",
                albumCount = 0,
                songCount = 1,
                genre = listOf(
                    MusicAttribute(id = "348", name = "Hip Hop")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=1859&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 297,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "2816",
                name = "...And Oceans",
                albumCount = 6,
                songCount = 69,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "2", name = "Metal"),
                    MusicAttribute(id = "11", name = "Death Metal"),
                    MusicAttribute(id = "21", name = "Black Metal"),
                    MusicAttribute(id = "58", name = "Melodic Death Metal")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=2816&object_type=artist&id=18648&name=art.jpg",
                flag = 0,
                summary = null,
                time = 39546,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "2967",
                name = "(ghost)",
                albumCount = 0,
                songCount = 0,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "476", name = "IDM"),
                    MusicAttribute(id = "395", name = "Glitch")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=2967&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 2603,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "4320",
                name = "[:SITD:]",
                albumCount = 0,
                songCount = 2,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "226", name = "Dark Electro"),
                    MusicAttribute(id = "224", name = "Electro-Industrial"),
                    MusicAttribute(id = "433", name = "Aggrotech"),
                    MusicAttribute(id = "512", name = "Futurepop"),
                    MusicAttribute(id = "511", name = "Synthpop")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=4320&object_type=artist&id=21227&name=art.jpg",
                flag = 0,
                summary = null,
                time = 1555,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "5166",
                name = "[AMATORY]",
                albumCount = 28,
                songCount = 351,
                genre = listOf(
                    MusicAttribute(id = "2", name = "Metal"),
                    MusicAttribute(id = "11", name = "Death Metal"),
                    MusicAttribute(id = "3", name = "Rock"),
                    MusicAttribute(id = "19", name = "Metalcore"),
                    MusicAttribute(id = "348", name = "Hip Hop"),
                    MusicAttribute(id = "9", name = "Hardcore"),
                    MusicAttribute(id = "20", name = "Heavy Metal"),
                    MusicAttribute(id = "334", name = "Dubstep"),
                    MusicAttribute(id = "28", name = "Alternative"),
                    MusicAttribute(id = "58", name = "Melodic Death Metal")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=5166&object_type=artist&id=21765&name=art.jpg",
                flag = 0,
                summary = null,
                time = 81680,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "1811",
                name = "*NSYNC",
                albumCount = 4,
                songCount = 46,
                genre = listOf(
                    MusicAttribute(id = "15", name = "Pop")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=1811&object_type=artist&id=16985&name=art.jpg",
                flag = 0,
                summary = null,
                time = 10683,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "5192",
                name = "#####",
                albumCount = 20,
                songCount = 168,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "2", name = "Metal"),
                    MusicAttribute(id = "3", name = "Rock"),
                    MusicAttribute(id = "19", name = "Metalcore"),
                    MusicAttribute(id = "9", name = "Hardcore"),
                    MusicAttribute(id = "53", name = "Industrial"),
                    MusicAttribute(id = "15", name = "Pop"),
                    MusicAttribute(id = "28", name = "Alternative"),
                    MusicAttribute(id = "123", name = "Alternative Rock"),
                    MusicAttribute(id = "435", name = "Russian")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=5192&object_type=artist&id=21864&name=art.jpg",
                flag = 0,
                summary = null,
                time = 40023,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "935",
                name = "<code>",
                albumCount = 18,
                songCount = 124,
                genre = listOf(
                    MusicAttribute(id = "2", name = "Metal"),
                    MusicAttribute(id = "21", name = "Black Metal"),
                    MusicAttribute(id = "235", name = "Avantgarde"),
                    MusicAttribute(id = "83", name = "Progressive"),
                    MusicAttribute(id = "237", name = "Post-Black")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=935&object_type=artist&id=13520&name=art.jpg",
                flag = 0,
                summary = null,
                time = 55513,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "4029",
                name = "1000 Homo DJs",
                albumCount = 0,
                songCount = 0,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "3", name = "Rock")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=4029&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 365,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "4030",
                name = "1000 Homo DJs",
                albumCount = 0,
                songCount = 0,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "3", name = "Rock")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=4030&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 365,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "5079",
                name = "13th Angel",
                albumCount = 0,
                songCount = 1,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=5079&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 582,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "5080",
                name = "13th Angel",
                albumCount = 0,
                songCount = 0,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=5080&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 291,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "758",
                name = "156/Silence",
                albumCount = 1,
                songCount = 15,
                genre = listOf(
                    MusicAttribute(id = "2", name = "Metal"),
                    MusicAttribute(id = "35", name = "Deathcore"),
                    MusicAttribute(id = "193", name = "Melodic Metallic Hardcore")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=758&object_type=artist&id=11379&name=art.jpg",
                flag = 0,
                summary = null,
                time = 3048,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "1275",
                name = "1914",
                albumCount = 1,
                songCount = 10,
                genre = listOf(
                    MusicAttribute(id = "2", name = "Metal"),
                    MusicAttribute(id = "11", name = "Death Metal"),
                    MusicAttribute(id = "21", name = "Black Metal"),
                    MusicAttribute(id = "125", name = "Doom Metal"),
                    MusicAttribute(id = "152", name = "Blackened Death Metal"),
                    MusicAttribute(id = "274", name = "Death-Doom Metal")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=1275&object_type=artist&id=14822&name=art.jpg",
                flag = 0,
                summary = null,
                time = 3418,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "956",
                name = "1ST VOWS",
                albumCount = 0,
                songCount = 1,
                genre = listOf(),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=956&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 337,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "3704",
                name = "2 Bullet",
                albumCount = 0,
                songCount = 1,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "224", name = "Electro-Industrial"),
                    MusicAttribute(id = "512", name = "Futurepop")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=3704&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 652,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "688",
                name = "200 Stab Wounds",
                albumCount = 1,
                songCount = 9,
                genre = listOf(
                    MusicAttribute(id = "11", name = "Death Metal")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=688&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 1762,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "3727",
                name = "2016 Single",
                albumCount = 0,
                songCount = 1,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "224", name = "Electro-Industrial"),
                    MusicAttribute(id = "512", name = "Futurepop")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=3727&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 614,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "4976",
                name = "24Bush",
                albumCount = 0,
                songCount = 0,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "510", name = "Jungle")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=4976&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 871,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "4977",
                name = "24Bush",
                albumCount = 0,
                songCount = 0,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "510", name = "Jungle")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=4977&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 871,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "2458",
                name = "27.Fuckdemons",
                albumCount = 0,
                songCount = 1,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "16", name = "Score")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=2458&object_type=artist&id=21554&name=art.jpg",
                flag = 0,
                summary = null,
                time = 326,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "3388",
                name = "2Bullet",
                albumCount = 0,
                songCount = 4,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "226", name = "Dark Electro"),
                    MusicAttribute(id = "433", name = "Aggrotech"),
                    MusicAttribute(id = "225", name = "Post-Industrial"),
                    MusicAttribute(id = "9", name = "Hardcore"),
                    MusicAttribute(id = "589", name = "Freeform"),
                    MusicAttribute(id = "590", name = "Psytrance")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=3388&object_type=artist&id=19247&name=art.jpg",
                flag = 0,
                summary = null,
                time = 2210,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "2514",
                name = "2methyl",
                albumCount = 5,
                songCount = 28,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "225", name = "Post-Industrial"),
                    MusicAttribute(id = "469", name = "Neurofunk"),
                    MusicAttribute(id = "334", name = "Dubstep")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=2514&object_type=artist&id=18704&name=art.jpg",
                flag = 0,
                summary = null,
                time = 18986,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "4410",
                name = "2nd Face",
                albumCount = 0,
                songCount = 1,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "226", name = "Dark Electro"),
                    MusicAttribute(id = "224", name = "Electro-Industrial"),
                    MusicAttribute(id = "433", name = "Aggrotech")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=4410&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 698,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "4411",
                name = "2nd Face",
                albumCount = 0,
                songCount = 0,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "226", name = "Dark Electro"),
                    MusicAttribute(id = "224", name = "Electro-Industrial"),
                    MusicAttribute(id = "433", name = "Aggrotech")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=4411&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 349,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "1413",
                name = "3 Doors Down",
                albumCount = 2,
                songCount = 24,
                genre = listOf(
                    MusicAttribute(id = "123", name = "Alternative Rock")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=1413&object_type=artist&id=17486&name=art.jpg",
                flag = 0,
                summary = null,
                time = 5802,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "5120",
                name = "30 Seconds To Mars",
                albumCount = 8,
                songCount = 101,
                genre = listOf(
                    MusicAttribute(id = "3", name = "Rock"),
                    MusicAttribute(id = "87", name = "Instrumental"),
                    MusicAttribute(id = "5", name = "Progressive Rock"),
                    MusicAttribute(id = "212", name = "Pop Rock")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=5120&object_type=artist&id=21373&name=art.jpg",
                flag = 0,
                summary = null,
                time = 25824,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "5165",
                name = "3000 Миль До Рая",
                albumCount = 5,
                songCount = 28,
                genre = listOf(
                    MusicAttribute(id = "3", name = "Rock"),
                    MusicAttribute(id = "28", name = "Alternative"),
                    MusicAttribute(id = "435", name = "Russian"),
                    MusicAttribute(id = "720", name = "Emocore")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=5165&object_type=artist&id=21757&name=art.jpg",
                flag = 0,
                summary = null,
                time = 6534,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "2318",
                name = "37 Heartbreak",
                albumCount = 0,
                songCount = 2,
                genre = listOf(
                    MusicAttribute(id = "16", name = "Score"),
                    MusicAttribute(id = "447", name = "Rap")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=2318&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 726,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "1793",
                name = "3LW",
                albumCount = 0,
                songCount = 1,
                genre = listOf(
                    MusicAttribute(id = "348", name = "Hip Hop")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=1793&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 253,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "1414",
                name = "3OH!3",
                albumCount = 1,
                songCount = 1,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=1414&object_type=artist&id=17483&name=art.jpg",
                flag = 0,
                summary = null,
                time = 202,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "219",
                name = "3TEETH",
                albumCount = 0,
                songCount = 1,
                genre = listOf(),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=219&object_type=artist&id=2980&name=art.jpg",
                flag = 0,
                summary = "3TEETH is an American industrial rock band from Los Angeles, California, United States that formed in 2013. The band cites Front Line Assembly, Tool, and Ministry as influences while incorporating 90s Wax Trax-style ebm and gritty tracks that sound like the spawn of Nine Inch Nails and early KMFDM. Their self-titled debut album was released 30 June 2014 on Artoffact Records.\n\nA remix album entitled REMIXED was released 21 October 2014. It features tracks originally from the debut album 3TEETH remixed by artists like Aesthetic Perfection ",
                time = 218,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "194",
                name = "3TEETH, Mick Gordon",
                albumCount = 2,
                songCount = 1,
                genre = listOf(),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=194&object_type=artist&id=2956&name=art.jpg",
                flag = 0,
                summary = null,
                time = 223,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "2832",
                name = "3xil3",
                albumCount = 0,
                songCount = 0,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "124", name = "Edm"),
                    MusicAttribute(id = "469", name = "Neurofunk")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=2832&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 1308,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "5160",
                name = "4 Апреля",
                albumCount = 9,
                songCount = 71,
                genre = listOf(
                    MusicAttribute(id = "3", name = "Rock"),
                    MusicAttribute(id = "28", name = "Alternative"),
                    MusicAttribute(id = "435", name = "Russian"),
                    MusicAttribute(id = "722", name = "Runk")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=5160&object_type=artist&id=21732&name=art.jpg",
                flag = 0,
                summary = null,
                time = 15096,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "4932",
                name = "4-Mat",
                albumCount = 0,
                songCount = 0,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "286", name = "chiptune"),
                    MusicAttribute(id = "443", name = "Disco"),
                    MusicAttribute(id = "700", name = "Tracker Module")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=4932&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 1910,
                yearFormed = 0,
                placeFormed = null
            ),
            Artist(
                id = "4933",
                name = "4-Mat",
                albumCount = 0,
                songCount = 0,
                genre = listOf(
                    MusicAttribute(id = "131", name = "Electronic"),
                    MusicAttribute(id = "286", name = "chiptune"),
                    MusicAttribute(id = "700", name = "Tracker Module")
                ),
                artUrl = "https://powerampache.devilplan.com/image.php?object_id=4933&object_type=artist&name=art.jpg",
                flag = 0,
                summary = null,
                time = 2265,
                yearFormed = 0,
                placeFormed = null
            )
        )
    }
}