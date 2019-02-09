package ke.co.appslab.jetpacknavcontroller.ui.bookdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ke.co.appslab.jetpacknavcontroller.R
import ke.co.appslab.jetpacknavcontroller.ui.workdetails.AuthorsAdapter
import ke.co.appslab.jetpacknavcontroller.utils.initToolbar
import kotlinx.android.synthetic.main.fragment_book_details.*

class BookDetailsFragment : Fragment() {
    private lateinit var viewModel: BookDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookDetailsViewModel::class.java)

        initToolbar(toolbar, 0, true)
        initDetails()

        viewModel.loadArguments(arguments)
    }

    private fun initDetails() {
        viewModel.book.observe(this, Observer {
            when {
                it?.cover?.medium != null -> Glide.with(this)
                    .load(it.cover.medium)
                    .error(Glide.with(this).load(R.drawable.book_cover_missing))
                    .into(ivCover)
                else -> Glide.with(this)
                    .load(R.drawable.book_cover_missing)
                    .into(ivCover)
            }

            toolbar.title = it?.title
            toolbar.subtitle = it?.subtitle

            tvNumberOfPages.text = getString(R.string.number_of_pages, it?.numberOfPages)
            tvPublishedYear.text = getString(R.string.published_year, it?.publishDate)

            val adapter = AuthorsAdapter(it?.authors ?: ArrayList()){
                // TODO implement navigation to author details
            }

            rvAuthors.adapter = adapter
            rvAuthors.layoutManager = LinearLayoutManager(activity!!)
        })
    }
}