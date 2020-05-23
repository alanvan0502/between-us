package com.example.betweenus.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.betweenus.R
import com.example.betweenus.user_account.login.LoginActivity
import com.example.domain.base.data
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModel<MainViewModel>()

    private lateinit var sectionPagerAdapter: SectionPagerAdapter

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPagerAdapter()
        setupFab()

        setupViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                mainViewModel.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        mainViewModel.apply {
            getAuthStatusFlow()
            authDataLiveData.observe(this@MainActivity, Observer {
                if (it.data?.uid == null) {
                    goToLoginActivity()
                }
            })
        }
    }

    private fun goToLoginActivity() {
        startActivity(LoginActivity.newIntent(this))
        finish()
    }

    private fun setupPagerAdapter() {
        setSupportActionBar(toolbar)
        sectionPagerAdapter = SectionPagerAdapter(this)
        container.adapter = sectionPagerAdapter
    }


    private fun setupFab() {
        fab.setOnClickListener {
            Snackbar.make(it, "Replace with action", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
        }
    }
}
