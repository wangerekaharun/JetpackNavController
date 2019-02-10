package ke.co.appslab.jetpacknavcontroller.ui.workdetails

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ke.co.appslab.jetpacknavcontroller.api.ApiClient
import ke.co.appslab.jetpacknavcontroller.api.ApiService
import ke.co.appslab.jetpacknavcontroller.data.BookDataSourceFactory
import ke.co.appslab.jetpacknavcontroller.models.Work
import ke.co.appslab.jetpacknavcontroller.repository.FavoritesRepository
import ke.co.appslab.jetpacknavcontroller.utils.NetworkState

class WorkDetailsViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val WORK_ARGUMENT = "work"

        fun createArguments(work: Work): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(WORK_ARGUMENT, work)

            return bundle
        }
    }

    private val favoritesRepository = FavoritesRepository(application)

    private val bookDataSourceFactory = BookDataSourceFactory(
        ApiClient().getClient().create(ApiService::class.java)
    )

    private val pagingConfig = PagedList.Config.Builder()
        .setPageSize(10)
        .setPrefetchDistance(10)
        .setEnablePlaceholders(true)
        .build()

    val data = LivePagedListBuilder(bookDataSourceFactory, pagingConfig)
        .build()

    val networkState: LiveData<NetworkState> =
        Transformations.switchMap(bookDataSourceFactory.sourceLiveData) {
            it.networkState
        }

    val work: LiveData<Work> = Transformations.map(bookDataSourceFactory.work) { it }

    var favorite: LiveData<Work> = MutableLiveData()

    fun loadArguments(arguments: Bundle?) {
        when (arguments) {
            null -> return
            else -> {
                val work: Work? = arguments.get(WORK_ARGUMENT) as Work?
                when {
                    work != null -> {
                        favorite = favoritesRepository.getFavorite(work.id)

                        bookDataSourceFactory.work.postValue(work)
                        bookDataSourceFactory.sourceLiveData.value?.invalidate()
                    }
                }
            }
        }

    }

    fun addAsFavorite(): Boolean {
        val work = this.work.value

        return when {
            work != null -> {
                favoritesRepository.addFavorite(work)
                true
            }
            else -> false
        }
    }

    fun removeFromFavorites(): Boolean {
        val work = this.work.value

        return when {
            work != null -> {
                favoritesRepository.removeFavorite(work)
                true
            }
            else -> false
        }
    }
}