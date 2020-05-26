package com.example.betweenus.user_account.user_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.account.data.User
import com.example.domain.account.usecase.ObserveUserUseCase
import com.example.domain.base.BUResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class UserProfileViewModel : ViewModel() {

    private val observeUserUseCase by inject(ObserveUserUseCase::class.java)

    private val _userLiveData = MutableLiveData<BUResult<User>>()
    val userLiveData: LiveData<BUResult<User>> = _userLiveData

    fun getUserFlow() {
        viewModelScope.launch {
            observeUserUseCase(Any()).collect {
                _userLiveData.value = it
            }
        }
    }
}