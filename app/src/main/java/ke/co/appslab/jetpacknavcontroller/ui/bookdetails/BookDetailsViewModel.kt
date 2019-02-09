package ke.co.appslab.jetpacknavcontroller.ui.bookdetails

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ke.co.appslab.jetpacknavcontroller.models.Book

class BookDetailsViewModel : ViewModel() {
    companion object {
        private const val BOOK_ARGUMENT = "book"

        fun createArguments(book: Book): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(BOOK_ARGUMENT, book)

            return bundle
        }
    }

    val book: MutableLiveData<Book> = MutableLiveData()

    fun loadArguments(arguments: Bundle?) {
        if (arguments == null) {
            return
        }

        val book: Book? = arguments.get(BOOK_ARGUMENT) as Book?
        this.book.postValue(book)
    }
}