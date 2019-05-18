package ke.co.appslab.jetpacknavcontroller.models

import androidx.room.Entity

@Entity
data class AlbumSongRef(
    val ref_song_id: Int,
    val ref_album_id: Int

)