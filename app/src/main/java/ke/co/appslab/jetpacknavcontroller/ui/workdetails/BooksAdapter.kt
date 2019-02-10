package ke.co.appslab.jetpacknavcontroller.ui.workdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ke.co.appslab.jetpacknavcontroller.R
import ke.co.appslab.jetpacknavcontroller.models.Book
import kotlinx.android.synthetic.main.item_book.view.*

class BooksAdapter(
    private val glide: RequestManager,
    private val itemClickListener: (Book) -> Unit
) : PagedListAdapter<Book, BooksAdapter.MyViewHolder>(BOOK_COMPARATOR) {

    inner class MyViewHolder(itemView: View, val itemClickListener: (Book) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val coverImg: ImageView = itemView.coverImg
        private val titleText: TextView = itemView.titleText
        private val publishedText: TextView = itemView.publishedYearText

        fun bindBook(book: Book) {
            with(book) {
                glide.load(cover.medium)
                    .error(glide.load(R.drawable.book_cover_missing))
                    .into(coverImg)

                titleText.text = title
                publishedText.text = itemView.context.getString(R.string.published_year, publishDate)

                itemView.setOnClickListener {
                    itemClickListener(this)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book, parent, false)
            , itemClickListener
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bindBook(it) }
    }

    companion object {
        val BOOK_COMPARATOR = object : DiffUtil.ItemCallback<Book>() {
            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem.url == newItem.url
        }
    }
}