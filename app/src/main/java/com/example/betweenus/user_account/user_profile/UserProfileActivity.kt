package com.example.betweenus.user_account.user_profile

import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.betweenus.R
import com.example.betweenus.user_account.BaseActivity
import com.example.domain.base.data
import kotlinx.android.synthetic.main.activity_user_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

class UserProfileActivity : BaseActivity() {

    private val viewModel: UserProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModel()
        observeUserData()
    }

    private fun observeUserData() {
        viewModel.userLiveData.observe(this, Observer { userResult ->
            userResult.data?.let {
                name_input.setText(it.name)
                phone_input.setText(it.phoneNumber)
                email_input.setText(it.email)
                Glide.with(this).load(it.photoUrl).circleCrop().error(
                    Glide.with(this).load(R.drawable.default_user).circleCrop()
                ).into(profile_picture)
            }
        })
    }

    private fun setupViewModel() {
        viewModel.getUserFlow()
    }

}