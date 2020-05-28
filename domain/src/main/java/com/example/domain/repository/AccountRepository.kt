package com.example.domain.repository

import com.example.domain.account.data.AuthData
import com.example.domain.account.data.SignInData
import com.example.domain.account.data.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun isUserSignedIn(): Boolean
    suspend fun signIn(data: SignInData)
    fun observeAuthStatus(): Flow<AuthData?>
    fun observeUser(): Flow<User?>
    fun logout()
    suspend fun storageGetProfilePicUrl(): String?
    suspend fun storageUploadProfilePic(pictureAbsPath: String)
    suspend fun storageDeleteProfilePic()
    suspend fun fireStoreSaveUser(user: User)
    suspend fun authCreateUser(email: String, password: String)
    suspend fun authUpdateUser(email: String? = null, password: String? = null)
}