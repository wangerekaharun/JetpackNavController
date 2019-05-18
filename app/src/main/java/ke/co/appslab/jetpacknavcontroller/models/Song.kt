package ke.co.appslab.jetpacknavcontroller.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity
@Fts4
data class Song(
    @PrimaryKey
    @ColumnInfo(name = "rowId")
    val id: Long,
    val url: String,
    val songName: String,
    val artistName: String,
    val albumName: String
)