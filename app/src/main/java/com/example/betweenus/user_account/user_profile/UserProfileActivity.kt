package com.example.betweenus.user_account.user_profile

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.betweenus.R
import com.example.betweenus.helper.loadPictureCircleCrop
import com.example.betweenus.helper.toStringOrEmptyString
import com.example.betweenus.user_account.BaseActivity
import com.example.domain.base.data
import kotlinx.android.synthetic.main.activity_user_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

class UserProfileActivity : BaseActivity() {

    private val viewModel: UserProfileViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_user_profile

    override fun onCreateActivity() {
        super.onCreateActivity()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getUserFlow()
        setupChangeProfilePicButton()
        setupApplyChangesButton()
        observeCamera()
    }

    private fun setupChangeProfilePicButton() {
        change_profile_pic.setOnClickListener {
            val intent = viewModel.getCameraIntent()
            startActivityForResult(intent, viewModel.cameraRequestCode)
        }
    }

    private fun setupApplyChangesButton() {
        viewModel.applyChangesLiveData.observe(this, Observer {
            observeResultStates(it)
        })
        apply_changes.setOnClickListener {
            viewModel.applyChanges(
                name = name_input.text.toStringOrEmptyString(),
                email = email_input.text.toStringOrEmptyString()
            )
        }
    }

    private fun observeCamera() {
        viewModel.cameraError.observe(this, Observer {
            it?.let {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.takenPicture.observe(this, Observer {
            val bitmap = it ?: return@Observer
            profile_picture.loadPictureCircleCrop(this, bitmap, R.drawable.default_user)
        })
    }

    private fun getUserFlow() {
        viewModel.getUserFlow()
        viewModel.userLiveData.observe(this, Observer { userResult ->
            userResult.data?.let {
                name_input.setText(it.name)
                email_input.setText(it.email)
                it.photoUrl?.let { url ->
                    profile_picture.loadPictureCircleCrop(this, url, R.drawable.default_user)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == viewModel.cameraRequestCode && resultCode == RESULT_OK) {
            viewModel.onPictureTaken()
        }
    }

}