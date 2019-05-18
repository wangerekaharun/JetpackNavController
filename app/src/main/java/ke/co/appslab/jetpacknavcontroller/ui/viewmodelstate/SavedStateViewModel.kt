package ke.co.appslab.jetpacknavcontroller.ui.viewmodelstate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class SavedStateViewModel(val handle : SavedStateHandle) : ViewModel(){

    fun test(){
        viewModelScope
    }
}