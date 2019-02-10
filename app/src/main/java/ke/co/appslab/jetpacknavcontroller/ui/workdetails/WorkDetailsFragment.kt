package ke.co.appslab.jetpacknavcontroller.ui.workdetails

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ke.co.appslab.jetpacknavcontroller.R
import ke.co.appslab.jetpacknavcontroller.models.Author
import ke.co.appslab.jetpacknavcontroller.models.Work
import ke.co.appslab.jetpacknavcontroller.utils.CoverSize
import ke.co.appslab.jetpacknavcontroller.utils.NetworkState
import ke.co.appslab.jetpacknavcontroller.utils.initToolbar
import ke.co.appslab.jetpacknavcontroller.utils.loadCover
import kotlinx.android.synthetic.main.fragment_work_details.*
import org.jetbrains.anko.toast

class WorkDetailsFragment : Fragment() {
    private lateinit var workDetailsViewModel: WorkDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_work_details, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.work_details, menu)

        // Make menu items invisible until details are loaded.
        menu.findItem(R.id.menuAddFavorite)?.isVisible = false
        menu.findItem(R.id.menuRemoveFavorite)?.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuAddFavorite -> workDetailsViewModel.addAsFavorite()
            R.id.menuRemoveFavorite -> workDetailsViewModel.removeFromFavorites()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        workDetailsViewModel = ViewModelProviders.of(this).get(WorkDetailsViewModel::class.java)

        initToolbar(toolbar, 0, true)
        initDetails()
        initEditionsAdapter()

        toolbar.postDelayed({ workDetailsViewModel.loadArguments(arguments) }, 100)
    }

    private fun initDetails() {
        workDetailsViewModel.work.observe(this, Observer { work ->
            activity?.toast(work.toString())
            Log.d("WorkSent", work.toString())
            when {
                work?.coverId != null -> Glide.with(this)
                    .loadCover(work.coverId, CoverSize.M)
                    .error(Glide.with(this).load(R.drawable.book_cover_missing))
                    .into(ivCover)
                else -> Glide.with(this)
                    .load(R.drawable.book_cover_missing)
                    .into(ivCover)
            }

            toolbar.title = work?.title
            toolbar.subtitle = work?.subtitle

            workDetailsViewModel.favorite.observe(this@WorkDetailsFragment, Observer { favorite ->
                when {
                    favorite != null -> {
                        toolbar.menu.findItem(R.id.menuAddFavorite)?.isVisible = false
                        toolbar.menu.findItem(R.id.menuRemoveFavorite)?.isVisible = true
                    }
                    else -> {
                        toolbar.menu.findItem(R.id.menuAddFavorite)?.isVisible = true
                        toolbar.menu.findItem(R.id.menuRemoveFavorite)?.isVisible = false
                    }
                }
            })

            val adapter = AuthorsAdapter(getAuthors(work)) {
                //TODO implement navigation to Author details
            }
            rvAuthors.adapter = adapter
            rvAuthors.layoutManager = LinearLayoutManager(activity!!)

            val numberOfEditions = work?.editionIsbns?.size ?: 0

            tvEditions.text = resources.getQuantityString(
                R.plurals.editions_available,
                numberOfEditions, numberOfEditions
            )
        })
    }

    private fun initEditionsAdapter() {
        val adapter = BooksAdapter(Glide.with(this)) {
            findNavController().navigate(R.id.actionShowEdition)
        }

        rvEditions.adapter = adapter
        rvEditions.layoutManager = LinearLayoutManager(activity!!)

        workDetailsViewModel.data.observe(this, Observer {
            adapter.submitList(it)
        })

        workDetailsViewModel.networkState.observe(this, Observer {
            progressBar.visibility = when (it) {
                NetworkState.LOADING -> View.VISIBLE
                else -> View.GONE
            }
        })
    }

    private fun getAuthors(it: Work?): List<Author> {
        val authors = ArrayList<Author>()

        when {
            it?.authorName?.size != null -> for (i in 0 until it.authorName.size) {
                authors.add(
                    Author(
                        it.authorName[i],
                        it.authorKey[i]
                    )
                )
            }
        }

        return authors
    }
}
