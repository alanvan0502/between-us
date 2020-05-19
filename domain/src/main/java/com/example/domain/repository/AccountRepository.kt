package com.example.domain.repository

import com.example.domain.account.data.SignInData
import com.example.domain.base.Result

interface AccountRepository {
    fun isUserSignedIn(): Boolean
    suspend fun signIn(data: SignInData): Result<Boolean>
}