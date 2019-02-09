package ke.co.appslab.jetpacknavcontroller.ui.launcher

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ke.co.appslab.jetpacknavcontroller.repository.FavoritesRepository

class LauncherViewModel(application: Application) : AndroidViewModel(application) {
    val favoriteCount: LiveData<Int> = FavoritesRepository(application).getFavoriteCount()
}