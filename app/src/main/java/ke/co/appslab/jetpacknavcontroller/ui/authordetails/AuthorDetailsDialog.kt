package ke.co.appslab.jetpacknavcontroller.ui.authordetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ke.co.appslab.jetpacknavcontroller.R
import kotlinx.android.synthetic.main.dialog_author_details.view.*

class AuthorDetailsDialog : DialogFragment() {
    private lateinit var viewModel: AuthorDetailsViewModel

    private lateinit var webView: WebView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.dialog_author_details, container, false)

        webView = rootView.webView
        val yesButton = rootView.okayBtn

        yesButton.setOnClickListener { dismiss() }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthorDetailsViewModel::class.java)
        initAuthorWebView()

        viewModel.loadArguments(arguments)
    }

    private fun initAuthorWebView() {
        webView.webViewClient = WebViewClient()
        viewModel.author.observe(this, Observer {
            val url = it?.url
            val authorUrl = when {
                url?.startsWith(context?.getString(R.string.http_prefix) ?: "") == true -> url
                else -> context?.getString(R.string.author_page_url, url)
            }

            webView.loadUrl(authorUrl)
        })
    }

    companion object {
        const val TAG = "AuthorDetailsDialog.TAG"
    }
}