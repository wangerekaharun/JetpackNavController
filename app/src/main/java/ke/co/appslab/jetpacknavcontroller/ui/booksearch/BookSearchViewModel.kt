package ke.co.appslab.jetpacknavcontroller.ui.booksearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ke.co.appslab.jetpacknavcontroller.api.ApiClient
import ke.co.appslab.jetpacknavcontroller.api.ApiService
import ke.co.appslab.jetpacknavcontroller.data.WorkDataSourceFactory
import ke.co.appslab.jetpacknavcontroller.utils.NetworkState
import ke.co.appslab.jetpacknavcontroller.utils.SearchCriteria

class BookSearchViewModel : ViewModel() {
    companion object {
        private const val PAGE_SIZE = 100
    }

    private val workDataSourceFactory = WorkDataSourceFactory(
        ApiClient().getClient().create(ApiService::class.java)
    )

    private val pagingConfig = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setPrefetchDistance(PAGE_SIZE)
        .setEnablePlaceholders(true)
        .build()

    val data = LivePagedListBuilder(workDataSourceFactory, pagingConfig)
        .build()

    val networkState: LiveData<NetworkState> = Transformations.switchMap(workDataSourceFactory.sourceLiveData) {
        it.networkState
    }

    fun updateSearchTerm(searchTerm: String) {
        workDataSourceFactory.searchTerm.postValue(searchTerm)
        workDataSourceFactory.sourceLiveData.value?.invalidate()
    }

    fun updateSearchCriteria(searchCriteria: SearchCriteria) {
        workDataSourceFactory.searchCriteria.postValue(searchCriteria)
        workDataSourceFactory.sourceLiveData.value?.invalidate()
    }
}