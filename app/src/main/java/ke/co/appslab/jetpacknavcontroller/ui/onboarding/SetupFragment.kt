package ke.co.appslab.jetpacknavcontroller.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import ke.co.appslab.jetpacknavcontroller.R
import ke.co.appslab.jetpacknavcontroller.ui.viewmodelstate.SavedStateViewModel

class SetupFragment : Fragment() {
    private val onboaardingViewModel: OnboardingViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_work_details, container, false)
    }

    fun onSetupComplete(username: String) {
        onboaardingViewModel.username = username
    }


}