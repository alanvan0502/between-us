package com.example.betweenus.user_account.user_profile

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betweenus.managers.camera.CameraManager
import com.example.domain.account.data.User
import com.example.domain.account.usecase.ObserveUserUseCase
import com.example.domain.base.BUResult
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class UserProfileViewModel : ViewModel() {

    private val observeUserUseCase by inject(ObserveUserUseCase::class.java)
    private val cameraManager = CameraManager()

    private val _userLiveData = MutableLiveData<BUResult<User>>()
    val userLiveData: LiveData<BUResult<User>> = _userLiveData

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
                //TODO: save the picture to cloud storage
            } catch (e: Throwable) {
                _cameraError.value = e
            }
        }
    }
}