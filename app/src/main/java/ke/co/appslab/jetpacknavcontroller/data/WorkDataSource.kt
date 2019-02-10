package ke.co.appslab.jetpacknavcontroller.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import ke.co.appslab.jetpacknavcontroller.api.ApiService
import ke.co.appslab.jetpacknavcontroller.models.SearchResponse
import ke.co.appslab.jetpacknavcontroller.models.Work
import ke.co.appslab.jetpacknavcontroller.utils.NetworkState
import ke.co.appslab.jetpacknavcontroller.utils.SearchCriteria
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WorkDataSource(
    private val apiService: ApiService,
    private val searchTerm: String,
    private val searchCriteria: SearchCriteria
) : PageKeyedDataSource<Int, Work>() {

    val networkState = MutableLiveData<NetworkState>()

    fun getNextPageKey(firstItem: Int, itemCount: Int, pageSize: Int): Int? {
        return when {
            firstItem + pageSize < itemCount -> (firstItem + pageSize / pageSize) + 1
            else -> null
        }
    }

    private fun createRequest(searchTerm: String, searchCriteria: SearchCriteria, pageKey: Int) =
        when (searchCriteria) {
            SearchCriteria.AUTHOR -> apiService.searchByAuthor(searchTerm, pageKey)
            SearchCriteria.TITLE -> apiService.searchByTitle(searchTerm, pageKey)
            else -> apiService.search(searchTerm, pageKey)
        }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Work>) {
        // Ignored, since we only ever append to our initial load
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Work>) {
        networkState.postValue(NetworkState.LOADING)

        when {
            searchTerm.isEmpty() -> {
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(ArrayList<Work>(), null, null)
                return
            }
            // triggered by a refresh, we better execute sync
            else -> try {
                val response = createRequest(
                    searchTerm,
                    searchCriteria,
                    1
                ).execute()

                val data = response.body()
                val items = response.body()?.results ?: ArrayList()

                networkState.postValue(NetworkState.LOADED)

                callback.onResult(
                    items,
                    null,
                    getNextPageKey(data?.start ?: 0, data?.count ?: 0, params.requestedLoadSize)
                )
            } catch (ioException: IOException) {
                ioException.printStackTrace()

                val error = NetworkState.error(ioException.message ?: "unknown error")
                networkState.postValue(error)
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Work>) {
        createRequest(
            searchTerm,
            searchCriteria,
            params.key
        ).enqueue(object : Callback<SearchResponse> {

            override fun onResponse(call: Call<SearchResponse>?, response: Response<SearchResponse>?) {
                when {
                    response?.isSuccessful == true -> {
                        val data = response.body()
                        val items = response.body()?.results ?: ArrayList()

                        callback.onResult(
                            items,
                            getNextPageKey(data?.start ?: 0, data?.count ?: 0, params.requestedLoadSize)
                        )
                    }
                    else -> networkState.postValue(
                        NetworkState.error("error code: ${response?.code()}")
                    )
                }
            }

            override fun onFailure(call: Call<SearchResponse>?, t: Throwable?) {
                networkState.postValue(NetworkState.error(t?.message ?: "unknown err"))
            }
        })
    }
}