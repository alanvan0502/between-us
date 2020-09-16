package com.example.betweenus.user_account.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.account.data.AuthData
import com.example.domain.account.data.SignInData
import com.example.domain.account.usecase.ObserveAuthUseCase
import com.example.domain.account.usecase.SignInWithEmailAndPasswordUseCase
import com.example.domain.base.BUResult
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class LoginViewModel : ViewModel() {
    private val accountRepository by inject(AccountRepository::class.java)

    private val _signInLiveData = MutableLiveData<BUResult<Unit>>()
    val signInLiveData: LiveData<BUResult<Unit>> = _signInLiveData

    private val _userLiveData = MutableLiveData<BUResult<AuthData?>>()
    val authDataLiveData: LiveData<BUResult<AuthData?>> = _userLiveData

    private val signInWithEmailAndPasswordUseCase
    by inject(SignInWithEmailAndPasswordUseCase::class.java)

    private val observeAuthUseCase by inject(ObserveAuthUseCase::class.java)

    fun isUserSignedIn(): Boolean {
        return accountRepository.isUserSignedIn()
    }

    fun getAuthStatusFlow() {
        viewModelScope.launch {
            observeAuthUseCase(Any()).collect {
                _userLiveData.value = it
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _signInLiveData.value = BUResult.Loading
            _signInLiveData.value = signInWithEmailAndPasswordUseCase(
                params = SignInData(email, password)
            )
        }
    }
}
