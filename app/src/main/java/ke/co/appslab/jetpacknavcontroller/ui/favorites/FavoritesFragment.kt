package ke.co.appslab.jetpacknavcontroller.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import ke.co.appslab.jetpacknavcontroller.R
import ke.co.appslab.jetpacknavcontroller.ui.booksearch.WorksAdapter
import ke.co.appslab.jetpacknavcontroller.utils.HomeActivityDelegate
import ke.co.appslab.jetpacknavcontroller.utils.initToolbar
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {
    private lateinit var viewModel: FavoritesViewModel

    private lateinit var homeActivityDelegate: HomeActivityDelegate

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            homeActivityDelegate = context as HomeActivityDelegate
        } catch (e: ClassCastException) {
            throw ClassCastException("Host activity must implement MainActivity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)

        initToolbar(toolbar, R.string.favorites, false)
        homeActivityDelegate.setupNavDrawer(toolbar)
        homeActivityDelegate.enableNavDrawer(true)

        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshList()
    }

    private fun initAdapter() {
        val adapter = WorksAdapter(Glide.with(this)) {
            //TODO implement navigation to Work details
        }

        viewModel.data.observe(this, Observer {
            adapter.submitList(it)
        })

        rvWorks.adapter = adapter
    }
}