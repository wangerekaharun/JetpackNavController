package ke.co.appslab.jetpacknavcontroller.ui.viewmodelscope

import androidx.lifecycle.ViewModel
import ke.co.appslab.jetpacknavcontroller.repository.DataRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PreviousScopeViewModel : ViewModel(), CoroutineScope {
    private val previousScopeJob = SupervisorJob()
    private val dataRepo = DataRepo()

    override val coroutineContext: CoroutineContext
        get() = previousScopeJob + Dispatchers.Main


    fun loadData(){
        launch {
            dataRepo.loadData()
        }
    }

    override fun onCleared() {
        super.onCleared()
        previousScopeJob.cancel()
    }
}