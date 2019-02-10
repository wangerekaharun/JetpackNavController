package ke.co.appslab.jetpacknavcontroller.api

import ke.co.appslab.jetpacknavcontroller.api.ApiEndpoints.BASE_URL
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .addInterceptor(loggingInterceptor)
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