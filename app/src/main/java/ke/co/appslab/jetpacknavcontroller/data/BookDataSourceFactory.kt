package ke.co.appslab.jetpacknavcontroller.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ke.co.appslab.jetpacknavcontroller.api.ApiService
import ke.co.appslab.jetpacknavcontroller.models.Book
import ke.co.appslab.jetpacknavcontroller.models.Work

class BookDataSourceFactory(private val apiService: ApiService): DataSource.Factory<Int, Book>() {
    private val work = MutableLiveData<Work>()
    private val sourceLiveData = MutableLiveData<BookDataSource>()


    override fun create(): DataSource<Int, Book> {
        val source = BookDataSource(apiService, work.value)
        sourceLiveData.postValue(source)
        return source
    }
}