package ke.co.appslab.jetpacknavcontroller.models

import androidx.room.DatabaseView
import androidx.room.Embedded

@DatabaseView(
    """
    SELECT
    Album.*,
    count(song_id) AS numOfSongs,
    sum(song_elapsed_time) AS totalTime
    FROM Album
    JOIN AlbumSongRef ON (albumId = ref_album_id)
    JOIN Song ON (ref_song_id = song_id)
    GROUP by albumId
"""
)
data class AlbumItem(
    @Embedded
    val album: Album,
    val numOfSongs: Int,
    val totalTime: Long

)