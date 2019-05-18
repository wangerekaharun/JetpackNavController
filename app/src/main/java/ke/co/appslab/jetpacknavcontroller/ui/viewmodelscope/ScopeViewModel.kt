package ke.co.appslab.jetpacknavcontroller.ui.viewmodelscope

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ke.co.appslab.jetpacknavcontroller.repository.DataRepo
import kotlinx.coroutines.launch

class ScopeViewModel : ViewModel() {
    private val dataRepo = DataRepo()

    fun loadData(){
        viewModelScope.launch {
            dataRepo.loadData()
        }
    }
}