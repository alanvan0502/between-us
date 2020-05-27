package com.example.betweenus.user_account.user_profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

        change_profile_pic.setOnClickListener {
            val intent = viewModel.getCameraIntent()
            startActivityForResult(intent, viewModel.cameraRequestCode)
        }
        setupViewModel()
        observeUserData()
        observeCameraError()
    }

    private fun observeCameraError() {
        viewModel.cameraError.observe(this, Observer {
            it?.let {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        })
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == viewModel.cameraRequestCode && resultCode == RESULT_OK) {
            viewModel.onPictureTaken()
        }
    }

}