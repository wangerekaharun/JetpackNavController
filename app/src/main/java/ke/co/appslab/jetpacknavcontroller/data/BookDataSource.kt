package ke.co.appslab.jetpacknavcontroller.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import ke.co.appslab.jetpacknavcontroller.api.ApiClient
import ke.co.appslab.jetpacknavcontroller.api.ApiService
import ke.co.appslab.jetpacknavcontroller.models.Book
import ke.co.appslab.jetpacknavcontroller.models.Work
import ke.co.appslab.jetpacknavcontroller.utils.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class BookDataSource(
    private val apiService: ApiService,
    private val work: Work?): PageKeyedDataSource<Int, Book>() {
    val networkState = MutableLiveData<NetworkState>()

    fun getNextPageKey(startIndex: Int, count: Int): Int? {
        return when {
            startIndex + count < work?.editionIsbns?.size ?: 0 -> startIndex + count
            else -> null
        }
    }

    private fun createRequest(startIndex: Int, count: Int): Call<HashMap<String, Book>> {
        val isbns = work?.editionIsbns ?: ArrayList()
        val endIndex = when {
            isbns.size < startIndex + count -> isbns.size
            else -> startIndex + count
        }

        val query = when {
            !isbns.isEmpty() -> isbns.subList(startIndex, endIndex).map { "ISBN:$it" }.reduce { acc, s -> "$acc,$s" }
            else -> ""
        }

        return apiService.getBook(query)
    }

    override fun loadBefore(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, Book>) {
        // Ignored, since we only ever append to our initial load
    }

    override fun loadInitial(params: PageKeyedDataSource.LoadInitialParams<Int>, callback: PageKeyedDataSource.LoadInitialCallback<Int, Book>) {
        networkState.postValue(NetworkState.LOADING)

        try {
            val response = createRequest(0, params.requestedLoadSize)
                .execute()

            val data = response.body()
            val items = data?.values?.toList() ?: ArrayList()

            networkState.postValue(NetworkState.LOADED)

            callback.onResult(
                items,
                null,
                getNextPageKey(0, params.requestedLoadSize)
            )
        } catch (ioException: IOException) {
            ioException.printStackTrace()

            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
        }
    }

    override fun loadAfter(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, Book>) {
        createRequest(params.key, params.requestedLoadSize)
            .enqueue(object : Callback<HashMap<String, Book>> {

                override fun onResponse(call: Call<HashMap<String, Book>>?, response: Response<HashMap<String, Book>>?) {
                    when {
                        response?.isSuccessful == true -> {
                            val data = response.body()
                            val items = data?.values?.toList() ?: ArrayList()

                            callback.onResult(
                                items,
                                getNextPageKey(params.key, params.requestedLoadSize)
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<HashMap<String, Book>>?, t: Throwable?) {
                    networkState.postValue(NetworkState.error(t?.message ?: "unknown err"))
                }
            })
    }
}