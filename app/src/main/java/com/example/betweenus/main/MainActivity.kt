package com.example.betweenus.main

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.betweenus.R
import com.example.betweenus.helper.start
import com.example.betweenus.user_account.BaseActivity
import com.example.betweenus.user_account.login.LoginActivity
import com.example.betweenus.user_account.user_profile.UserProfileActivity
import com.example.domain.base.data
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val mainViewModel by viewModel<MainViewModel>()
    override var backPressToExitApp: Boolean = true
    override val layoutRes: Int = R.layout.activity_main

    private lateinit var sectionPagerAdapter: SectionPagerAdapter

    private lateinit var toolbar: Toolbar
    private lateinit var fab: FloatingActionButton
    private lateinit var viewPager: ViewPager2

    override fun onCreateActivity() {
        super.onCreateActivity()

        toolbar = findViewById(R.id.toolbar)
        fab = findViewById(R.id.fab)
        viewPager = findViewById(R.id.container)

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
            R.id.profile -> {
                this.start<UserProfileActivity>()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        mainViewModel.apply {
            getAuthStatusFlow()
            authLiveData.observe(this@MainActivity, Observer {
                if (it.data?.uid == null) {
                    start<LoginActivity>()
                }
            })
        }
    }

    private fun setupPagerAdapter() {
        setSupportActionBar(toolbar)
        sectionPagerAdapter = SectionPagerAdapter(this)
        viewPager.adapter = sectionPagerAdapter
    }


    private fun setupFab() {
        fab.setOnClickListener {
            Snackbar.make(it, "Replace with action", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
        }
    }
}
