package ke.co.appslab.jetpacknavcontroller.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import ke.co.appslab.jetpacknavcontroller.db.converters.Converters
import java.io.Serializable

@Entity(tableName = "Work")
@TypeConverters(Converters::class)
data class Work (
    @PrimaryKey
    @SerializedName("key")
    val id: String,

    @ColumnInfo(name = "cover_id")
    @SerializedName("cover_i")
    val coverId: String?,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = "subtitle")
    @SerializedName("subtitle")
    val subtitle: String?,

    @ColumnInfo(name = "author_name")
    @SerializedName("author_name")
    val authorName: List<String>,

    @ColumnInfo(name = "author_key")
    @SerializedName("author_key")
    val authorKey: List<String>,

    @ColumnInfo(name = "isbn")
    @SerializedName("isbn")
    val editionIsbns: List<String>
) : Serializable