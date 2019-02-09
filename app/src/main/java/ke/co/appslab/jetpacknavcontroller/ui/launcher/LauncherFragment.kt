package ke.co.appslab.jetpacknavcontroller.ui.launcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ke.co.appslab.jetpacknavcontroller.R

class LauncherFragment : Fragment(),LifecycleObserver {
    private lateinit var viewModel: LauncherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {


        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LauncherViewModel::class.java)

        viewModel.favoriteCount.observe(this, Observer {
            //TODO implement navigating to Search or Favorites
        })
    }

    private fun Int?.hasFavorites() = this != null && this > 0
}