package com.example.domain.repository

import com.example.domain.account.data.SignInData
import com.example.domain.account.data.User
import com.example.domain.base.Result
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun isUserSignedIn(): Boolean
    suspend fun signIn(data: SignInData)
    fun observeAuthStatus(): Flow<User?>
    fun logout()
}