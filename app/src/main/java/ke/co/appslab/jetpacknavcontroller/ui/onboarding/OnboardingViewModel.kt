package ke.co.appslab.jetpacknavcontroller.ui.onboarding

import androidx.lifecycle.ViewModel

class OnboardingViewModel : ViewModel() {
    lateinit var userName: String

    fun setUsername(name: String) {
        userName = name
    }

    fun getuserName(): String {
        return userName
    }
}