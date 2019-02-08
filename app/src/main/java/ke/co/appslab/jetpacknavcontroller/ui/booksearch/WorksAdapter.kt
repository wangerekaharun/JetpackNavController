package ke.co.appslab.jetpacknavcontroller.ui.booksearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import ke.co.appslab.jetpacknavcontroller.R
import ke.co.appslab.jetpacknavcontroller.models.Work
import ke.co.appslab.jetpacknavcontroller.utils.CoverSize
import ke.co.appslab.jetpacknavcontroller.utils.loadCover
import kotlinx.android.synthetic.main.item_work.view.*

class WorksAdapter(
    private val glide: RequestManager,
    val itemClickListener: (Work) -> Unit
) : PagedListAdapter<Work, WorksAdapter.MyViewHolder>(WORK_COMPARATOR) {

    inner class MyViewHolder(itemView: View,val itemClickListener: (Work) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val coverImg: ImageView = itemView.coverImg
        private val titleText = itemView.titleText
        private val authorText: TextView = itemView.authorText

        fun bindWork(work : Work){
            with(work){
                titleText.text = title
                authorText.text = authorName.reduce { acc, s -> "$acc, $s" }

                when{
                    coverId != null -> glide.loadCover(coverId,CoverSize.M)
                        .error(glide.load(R.drawable.book_cover_missing))
                        .into(coverImg)
                    else-> glide.load(R.drawable.book_cover_missing)
                        .into(coverImg)
                }
                itemView.setOnClickListener {
                    itemClickListener(this)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorksAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_work, parent, false)
        ,itemClickListener)
    }

    override fun onBindViewHolder(holder: WorksAdapter.MyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindWork(it)
        }
    }

    companion object {
        val WORK_COMPARATOR = object : DiffUtil.ItemCallback<Work>() {
            override fun areContentsTheSame(oldItem: Work, newItem: Work): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Work, newItem: Work): Boolean =
                oldItem.coverId == newItem.coverId
        }
    }
}