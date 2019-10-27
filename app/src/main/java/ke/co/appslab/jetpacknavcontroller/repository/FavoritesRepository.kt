package ke.co.appslab.jetpacknavcontroller.repository

import android.app.Application
import ke.co.appslab.jetpacknavcontroller.db.AppDataBase
import ke.co.appslab.jetpacknavcontroller.db.dao.FavoritesDao
import ke.co.appslab.jetpacknavcontroller.models.Work
import kotlinx.coroutines.flow.collect
import org.jetbrains.anko.doAsync

class FavoritesRepository(application: Application) {
    private val favoritesDao: FavoritesDao = AppDataBase.create(application).favoritesDao()

    fun getFavorites() = favoritesDao.getFavorites()

    fun getFavorite(id: String) = favoritesDao.getFavorite(id)

    fun getFavoriteCount() = favoritesDao.getFavoriteCount()

    fun addFavorite(work: Work) {
        doAsync {
            favoritesDao.addFavorite(work)
        }
    }

    fun removeFavorite(work: Work) {
        doAsync {
            favoritesDao.removeFavorite(work)
        }
    }

    suspend fun getAll(){
        favoritesDao.getAll().collect{work ->
            //handle work

        }
    }
}