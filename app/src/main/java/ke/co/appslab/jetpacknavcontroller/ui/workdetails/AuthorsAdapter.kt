package ke.co.appslab.jetpacknavcontroller.ui.workdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ke.co.appslab.jetpacknavcontroller.R
import ke.co.appslab.jetpacknavcontroller.models.Author
import kotlinx.android.synthetic.main.item_work.view.*

class AuthorsAdapter(
    private val authors : List<Author>,
    private val itemClickListener : (Author)-> Unit
) : RecyclerView.Adapter<AuthorsAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View, private val itemClickListener : (Author)-> Unit ):RecyclerView.ViewHolder(itemView) {
        private val authorText: TextView = itemView.authorText

        fun bindAuthor(author : Author){
            with(author){
                authorText.text = name
                itemView.setOnClickListener {
                    itemClickListener(this)
                }
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_author, parent, false)
        ,itemClickListener)
    }

    override fun getItemCount(): Int = authors.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindAuthor(authors[position])
    }
}