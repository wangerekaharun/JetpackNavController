package ke.co.appslab.jetpacknavcontroller.models

import androidx.room.Entity

@Entity
data class Album(
    val albumId: Int,
    val song_id : Int,
    val song_elapsed_time : Int
)