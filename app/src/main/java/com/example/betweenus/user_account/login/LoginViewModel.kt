package com.example.betweenus.user_account.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.account.data.SignInData
import com.example.domain.account.usecase.SignInWithEmailAndPasswordUseCase
import com.example.domain.base.Result
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class LoginViewModel : ViewModel() {
    private val context by inject(Context::class.java)
    private val accountManager by inject(AccountRepository::class.java)
    private val signInWithEmailAndPasswordUseCase
            by inject(SignInWithEmailAndPasswordUseCase::class.java)

    fun isUserSignedIn(): Boolean {
        return accountManager.isUserSignedIn()
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