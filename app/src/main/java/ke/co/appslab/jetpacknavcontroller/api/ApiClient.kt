package ke.co.appslab.jetpacknavcontroller.api

import ke.co.appslab.jetpacknavcontroller.api.ApiEndpoints.BASE_URL
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val client = OkHttpClient.Builder()
            .build()
    var retrofit: Retrofit? = null
    fun getClient(): Retrofit {
        when (retrofit) {
            null -> retrofit = Retrofit.Builder()
                .baseUrl(HttpUrl.parse(BASE_URL)!!)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofit as Retrofit
    }
}