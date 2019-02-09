package ke.co.appslab.jetpacknavcontroller.ui.authordetails

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ke.co.appslab.jetpacknavcontroller.models.Author

class AuthorDetailsViewModel : ViewModel() {
    companion object {
        private const val AUTHOR_ARGUMENT = "author"

        fun createArguments(autor: Author): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(AUTHOR_ARGUMENT, autor)

            return bundle
        }
    }

    val author: MutableLiveData<Author> = MutableLiveData()

    fun loadArguments(arguments: Bundle?) {
        if (arguments == null) {
            return
        }

        val author: Author? = arguments.get(AUTHOR_ARGUMENT) as Author
        this.author.postValue(author)
    }
}