package com.example.domain.repository

import com.example.domain.account.data.SignInData
import com.example.domain.account.data.SignUpData
import com.example.domain.account.data.AuthData
import com.example.domain.account.data.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun signIn(data: SignInData)
    suspend fun signUp(data: SignUpData)
    fun observeAuthStatus(): Flow<AuthData?>
    fun observeUser(): Flow<User?>
    fun isUserSignedIn(): Boolean
    fun logout()
}