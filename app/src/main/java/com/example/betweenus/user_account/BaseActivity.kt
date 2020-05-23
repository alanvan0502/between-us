package com.example.betweenus.user_account

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.betweenus.R
import com.example.domain.base.BUResult

abstract class BaseActivity : AppCompatActivity() {

    private var progressBar: ViewGroup? = null
    private var container: ViewGroup? = null

    protected open fun <T> observeResultStates(result: BUResult<T>) {
        when (result) {
            is BUResult.Loading -> showLoading()
            is BUResult.Success -> hideLoading()
            is BUResult.Error -> showError(result.throwable.toString())
        }
    }

    protected open fun bindViews() {
        progressBar = findViewById(R.id.progress_bar)
        container = findViewById(R.id.container)
    }

    protected open fun showLoading() {
        progressBar?.visibility = View.VISIBLE
        container?.visibility = View.GONE
    }

    protected open fun hideLoading() {
        progressBar?.visibility = View.GONE
        container?.visibility = View.VISIBLE
    }

    protected open fun showError(errorMessage: String) {
        hideLoading()
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}