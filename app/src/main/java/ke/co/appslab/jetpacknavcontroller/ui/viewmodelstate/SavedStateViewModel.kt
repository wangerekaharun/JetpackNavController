package ke.co.appslab.jetpacknavcontroller.ui.viewmodelstate

import android.provider.SyncStateContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class SavedStateViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    fun getUserName(): LiveData<String> {
        return savedStateHandle.getLiveData("username")
    }

    fun saveUserName(username: String) {
        savedStateHandle.set("username", username)
    }
}