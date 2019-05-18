package ke.co.appslab.jetpacknavcontroller.ui.viewmodelstate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateVMFactory
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.navGraphViewModels
import ke.co.appslab.jetpacknavcontroller.R
import kotlinx.android.synthetic.main.fragment_saved_state.*

class SavedStateFragment : Fragment() {
    private val savedStateViewModel : SavedStateViewModel by lazy {
        ViewModelProviders.of(this,SavedStateVMFactory(this)).get(SavedStateViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_state, container, false)
    }

    private fun setupActions() {
        saveBtn.setOnClickListener {
            savedStateViewModel.saveUserName(username_text_input_edit.text.toString())
        }
        savedStateViewModel.getUserName().observe(this, Observer {
            savedStateTextView.text= "LiveData Result: $it"
        })
    }

}