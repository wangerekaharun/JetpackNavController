package ke.co.appslab.jetpacknavcontroller.models

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("start") val start: Int,
    @SerializedName("numFound") val count: Int,
    @SerializedName("docs") val results: List<Work>
)