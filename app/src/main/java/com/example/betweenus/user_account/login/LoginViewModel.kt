package com.example.betweenus.user_account.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.account.data.SignInData
import com.example.domain.account.usecase.ObserveAuthUseCase
import com.example.domain.account.usecase.SignInWithEmailAndPasswordUseCase
import com.example.domain.base.Result
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class LoginViewModel : ViewModel() {
    private val context by inject(Context::class.java)
    private val accountRepository by inject(AccountRepository::class.java)

    private val signInWithEmailAndPasswordUseCase
            by inject(SignInWithEmailAndPasswordUseCase::class.java)

    private val observeAuthUseCase: ObserveAuthUseCase
            by inject(ObserveAuthUseCase::class.java)

    fun isUserSignedIn(): Boolean {
        return accountRepository.isUserSignedIn()
    }

    fun observeAuthStatus() {
        viewModelScope.launch {
            observeAuthUseCase(Any()).collect {
                when (it) {
                    is Result.Success -> {
                        Log.d("dmdmdm", it.data?.email.toString())
                    }
                    is Result.Error -> {
                        Log.d("dmdmdm", "Error")
                    }
                }
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            Log.d("dmdmdm", "Loading")
            val result = signInWithEmailAndPasswordUseCase(
                params = SignInData(email, password)
            )
            when (result) {
                is Result.Success -> {
                    Log.d("dmdmdm", "Success")
                }
                is Result.Error -> {
                    Log.d("dmdmdm", "Error")
                }
            }
        }
    }
}