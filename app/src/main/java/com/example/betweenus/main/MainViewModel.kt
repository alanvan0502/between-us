package com.example.betweenus.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.account.data.AuthData
import com.example.domain.account.usecase.LogoutUseCase
import com.example.domain.account.usecase.ObserveAuthUseCase
import com.example.domain.base.BUResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MainViewModel : ViewModel() {

    private val logoutUseCase by inject(LogoutUseCase::class.java)
    private val observeAuthUseCase by inject(ObserveAuthUseCase::class.java)

    private val _authLiveData = MutableLiveData<BUResult<AuthData?>>()
    val authLiveData: LiveData<BUResult<AuthData?>> = _authLiveData

    fun logout() {
        viewModelScope.launch {
            logoutUseCase(Any())
        }
    }

    fun getAuthStatusFlow() {
        viewModelScope.launch {
            observeAuthUseCase(Any()).collect {
                _authLiveData.value = it
            }
        }
    }
}