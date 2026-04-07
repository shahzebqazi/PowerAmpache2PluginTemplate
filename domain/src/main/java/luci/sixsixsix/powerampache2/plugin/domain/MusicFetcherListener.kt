package luci.sixsixsix.powerampache2.plugin.domain

interface MusicFetcherListener {
    fun getArtists(query: String = "")
    fun getAlbums(query: String = "")
    fun getSongsFromAlbum(albumId: String)
    fun getSongsFromPlaylist(playlistId: String)
    fun getAlbumsFromArtist(artistId: String)
}
