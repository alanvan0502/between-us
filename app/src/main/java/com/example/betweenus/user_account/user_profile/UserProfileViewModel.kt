package com.example.betweenus.user_account.user_profile

import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betweenus.managers.camera.CameraManager
import com.example.domain.account.data.User
import com.example.domain.account.usecase.ObserveUserUseCase
import com.example.domain.account.usecase.UpdateUserUseCase
import com.example.domain.account.usecase.UploadProfilePicUseCase
import com.example.domain.base.BUResult
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class UserProfileViewModel : ViewModel() {

    private val observeUserUseCase by inject(ObserveUserUseCase::class.java)
    private val uploadProfilePicUseCase by inject(UploadProfilePicUseCase::class.java)
    private val updateUserUseCase by inject(UpdateUserUseCase::class.java)
    private val cameraManager = CameraManager()

    private val _userLiveData = MutableLiveData<BUResult<User>>()
    val userLiveData: LiveData<BUResult<User>> = _userLiveData

    private val _applyChangesLiveData = MutableLiveData<BUResult<Unit>>()
    val applyChangesLiveData: LiveData<BUResult<Unit>> = _applyChangesLiveData

    private val _takenPicture = MutableLiveData<Bitmap?>(null)
    val takenPicture: LiveData<Bitmap?> = _takenPicture

    private val _cameraError: MutableLiveData<Throwable?> = MutableLiveData(null)
    val cameraError: LiveData<Throwable?> = _cameraError

    val cameraRequestCode
        get() = CameraManager.REQUEST_TAKE_PHOTO

    fun getUserFlow() {
        observeUserUseCase(Any()).onEach {
            _userLiveData.value = it
        }.launchIn(viewModelScope)
    }

    fun getCameraIntent(): Intent? {
        _cameraError.value = null
        return cameraManager.getCameraIntent()
    }

    fun onPictureTaken() {
        viewModelScope.launch {
            try {
                cameraManager.addPicToGallery()
                _takenPicture.value = cameraManager.getTakenPic()
            } catch (e: Throwable) {
                _cameraError.value = e
            }
        }
    }

    fun applyChanges(name: String, email: String) {
        viewModelScope.launch {
            _applyChangesLiveData.value = BUResult.Loading
            cameraManager.currentPhotoPath?.let { uploadProfilePicUseCase(it) }
            _applyChangesLiveData.value = updateUserUseCase(
                UpdateUserUseCase.Params(
                    name = name,
                    email = email
                )
            )
        }
    }
}