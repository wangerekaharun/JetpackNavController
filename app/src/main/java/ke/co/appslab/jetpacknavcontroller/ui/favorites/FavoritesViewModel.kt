package ke.co.appslab.jetpacknavcontroller.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ke.co.appslab.jetpacknavcontroller.repository.FavoritesRepository

class FavoritesViewModel(application: Application): AndroidViewModel(application) {
    companion object {
        private const val PAGE_SIZE = 100
    }

    private val favoritesRepository = FavoritesRepository(application)

    private val favoritesDataSourceFactory = favoritesRepository.getFavorites()

    private val pagingConfig = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setPrefetchDistance(PAGE_SIZE)
        .setEnablePlaceholders(true)
        .build()

    val data = LivePagedListBuilder(favoritesDataSourceFactory, pagingConfig)
        .build()

    fun refreshList() {
        data.value?.dataSource?.invalidate()
    }
}