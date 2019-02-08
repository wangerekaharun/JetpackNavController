package ke.co.appslab.jetpacknavcontroller.api

import ke.co.appslab.jetpacknavcontroller.api.ApiEndpoints.BASE_URL
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    fun create(): ApiClient = create(HttpUrl.parse(BASE_URL)!!)
    fun create(httpUrl: HttpUrl): ApiClient {
        val client = OkHttpClient.Builder()
            .build()
        return Retrofit.Builder()
            .baseUrl(httpUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiClient::class.java)
    }
}