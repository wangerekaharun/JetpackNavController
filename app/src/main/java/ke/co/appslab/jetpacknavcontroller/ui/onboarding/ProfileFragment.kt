package ke.co.appslab.jetpacknavcontroller.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import ke.co.appslab.jetpacknavcontroller.R

class ProfileFragment : Fragment() {
    private val onboaardingViewModel: OnboardingViewModel by navGraphViewModels(R.id.nav_graph)

    fun getUserName() = onboaardingViewModel.getuserName()
}