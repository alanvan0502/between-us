package com.example.betweenus.user_account.user_profile

import android.content.Intent
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.betweenus.R
import com.example.betweenus.helper.loadPictureCircleCrop
import com.example.betweenus.helper.toStringOrEmptyString
import com.example.betweenus.user_account.BaseActivity
import com.example.domain.base.data
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.koin.android.viewmodel.ext.android.viewModel

class UserProfileActivity : BaseActivity() {

    private val viewModel: UserProfileViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_user_profile

    private lateinit var changeProfilePic: ImageView
    private lateinit var applyChanges: MaterialButton
    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var profilePicture: ImageView

    override fun onCreateActivity() {
        super.onCreateActivity()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        changeProfilePic = findViewById(R.id.change_profile_pic)
        applyChanges = findViewById(R.id.apply_changes)
        nameInput = findViewById(R.id.name_input)
        emailInput = findViewById(R.id.email_input)
        profilePicture = findViewById(R.id.profile_picture)

        getUserFlow()
        setupChangeProfilePicButton()
        setupApplyChangesButton()
        observeCamera()
    }

    private fun setupChangeProfilePicButton() {
        changeProfilePic.setOnClickListener {
            val intent = viewModel.getCameraIntent()
            startActivityForResult(intent, viewModel.cameraRequestCode)
        }
    }

    private fun setupApplyChangesButton() {
        viewModel.applyChangesLiveData.observe(
            this,
            Observer {
                observeResultStates(it)
            }
        )
        applyChanges.setOnClickListener {
            viewModel.applyChanges(
                name = nameInput.text.toStringOrEmptyString(),
                email = emailInput.text.toStringOrEmptyString()
            )
        }
    }

    private fun observeCamera() {
        viewModel.cameraError.observe(
            this,
            Observer {
                it?.let {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        )
        viewModel.takenPicture.observe(
            this,
            Observer {
                val bitmap = it ?: return@Observer
                profilePicture.loadPictureCircleCrop(this, bitmap, R.drawable.default_user)
            }
        )
    }

    private fun getUserFlow() {
        viewModel.getUserFlow()
        viewModel.userLiveData.observe(
            this,
            Observer { userResult ->
                userResult.data?.let {
                    nameInput.setText(it.name)
                    emailInput.setText(it.email)
                    it.photoUrl?.let { url ->
                        profilePicture.loadPictureCircleCrop(this, url, R.drawable.default_user)
                    }
                }
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == viewModel.cameraRequestCode && resultCode == RESULT_OK) {
            viewModel.onPictureTaken()
        }
    }
}
