package ke.co.appslab.jetpacknavcontroller.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data  class Book(
    @SerializedName("url") val url: String,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("cover") val cover: Cover,
    @SerializedName("authors") val authors: List<Author>,
    @SerializedName("number_of_pages") val numberOfPages: Int,
    @SerializedName("publish_date") val publishDate: String,
    @SerializedName("description") val description: String
) : Serializable