package com.example.betweenus.user_account

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.betweenus.R
import com.example.betweenus.managers.BackPressManager
import com.example.domain.base.BUResult
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {

    private var progressBar: ViewGroup? = null
    private var container: ViewGroup? = null

    protected open var backPressToExitApp = false
    private val backPressManager: BackPressManager by inject()

    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateActivity()
    }

    @CallSuper
    protected open fun onCreateActivity() {
        setContentView(layoutRes)
        backPressManager.initialize(this.lifecycle)
        bindViews()
    }

    override fun onBackPressed() {
        if (!backPressToExitApp) {
            super.onBackPressed()
        } else {
            backPressManager.backPress(
                firstBackPressAction = {
                    Toast.makeText(this, getString(R.string.press_back_again), Toast.LENGTH_SHORT)
                        .show()
                },
                secondBackPressAction = {
                    finishAffinity()
                }
            )
        }
    }

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