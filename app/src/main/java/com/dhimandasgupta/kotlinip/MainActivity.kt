package com.dhimandasgupta.kotlinip

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by dhimandasgupta on 30/04/17.
 */
class MainActivity : AppCompatActivity() {
    private val SAVED_IP_TAG = "saved_ip"
    private var ipValue : String? = null

    // Variables may not be initialized if not required
    private var apiService : ApiService? = null
    private var disposable : Disposable? = null

    // Variables must be initialized later otherwise may get kotlin.UninitializedPropertyAccessException
    private lateinit var textView : AppCompatTextView
    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ipValue = savedInstanceState?.getString(SAVED_IP_TAG, null)

        textView = findViewById(R.id.activity_main_text_view) as AppCompatTextView
        progressBar = findViewById(R.id.activity_main_progress_bar) as ProgressBar
    }

    override fun onStart() {
        super.onStart()

        if (ipValue == null)
        {
            apiService = RestClient().createApi()

            disposable = apiService?.ip()
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ ip ->
                        showIp(ip)
                    }, { e ->
                        showError(e)
                    }, {
                        Toast.makeText(baseContext, "Completed", Toast.LENGTH_SHORT).show()
                    })
        } else {
            showText(ipValue)
        }
    }

    override fun onStop() {
        disposable?.dispose()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(SAVED_IP_TAG, ipValue)
        super.onSaveInstanceState(outState)
    }

    private fun showIp(ip : Ip) : Unit {
        ipValue = ip?.origin
        showText(ipValue)
    }

    private fun showError(e : Throwable) : Unit {
        ipValue = null
        showText(e.localizedMessage)
    }

    private fun showText(message : String?) : Unit {
        progressBar.visibility = View.GONE
        textView.visibility = View.VISIBLE
        textView.text = message
    }
}
