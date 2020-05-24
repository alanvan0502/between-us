package com.example.betweenus.user_account.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.account.data.SignUpData
import com.example.domain.account.usecase.SignUpUseCase
import com.example.domain.base.BUResult
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class SignUpViewModel : ViewModel() {

    private val signUpUseCase by inject(SignUpUseCase::class.java)
    private val accountRepository by inject(AccountRepository::class.java)

    private val _signUpLiveData = MutableLiveData<BUResult<Unit>>()
    val signUpLiveData = _signUpLiveData

    fun signUp(data: SignUpData) {
        viewModelScope.launch {
            _signUpLiveData.value = BUResult.Loading
            _signUpLiveData.value = signUpUseCase(data)
        }
    }

    fun isUserSignedIn() = accountRepository.isUserSignedIn()
}