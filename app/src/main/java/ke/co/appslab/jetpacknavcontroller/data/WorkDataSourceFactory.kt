package ke.co.appslab.jetpacknavcontroller.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ke.co.appslab.jetpacknavcontroller.api.ApiService
import ke.co.appslab.jetpacknavcontroller.models.Work
import ke.co.appslab.jetpacknavcontroller.utils.SearchCriteria

class WorkDataSourceFactory (private val apiService: ApiService): DataSource.Factory<Int, Work>() {
    val searchTerm = MutableLiveData<String>()
    val searchCriteria = MutableLiveData<SearchCriteria>()
    val sourceLiveData = MutableLiveData<WorkDataSource>()


    override fun create(): DataSource<Int, Work> {
        val source = WorkDataSource(
            apiService,
            searchTerm.value ?: "",
            searchCriteria.value ?: SearchCriteria.ALL
        )
        sourceLiveData.postValue(source)
        return source
    }
}