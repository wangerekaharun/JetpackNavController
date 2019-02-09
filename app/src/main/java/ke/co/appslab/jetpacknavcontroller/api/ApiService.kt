package ke.co.appslab.jetpacknavcontroller.api

import ke.co.appslab.jetpacknavcontroller.models.Book
import ke.co.appslab.jetpacknavcontroller.models.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/books?format=json&jscmd=data")
    fun getBook(@Query("bibkeys") searchQuery: String): Call<HashMap<String, Book>>

    @GET("search.json")
    fun searchByTitle(
        @Query("title") titleQuery: String,
        @Query("page") page: Int
    ): Call<SearchResponse>

    @GET("search.json")
    fun searchByAuthor(
        @Query("author") authorQuery: String,
        @Query("page") page: Int
    ): Call<SearchResponse>

    @GET("search.json")
    fun search(
        @Query("q") searchQuery: String,
        @Query("page") page: Int
    ): Call<SearchResponse>

}