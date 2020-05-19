package com.example.repository.account

import com.example.domain.account.data.SignInData
import com.example.domain.base.Result
import com.example.domain.repository.AccountRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.resume

class AccountRepositoryImpl : AccountRepository {

    private val auth by inject(FirebaseAuth::class.java)

    override fun isUserSignedIn(): Boolean {
        auth.currentUser
        return auth.currentUser != null
    }

    override suspend fun signIn(data: SignInData): Result<Boolean> {
        return suspendCancellableCoroutine { cont ->
            auth.signInWithEmailAndPassword(
                data.email, data.password
            ).addOnSuccessListener {
                cont.resume(Result.Success())
            }.addOnFailureListener {
                cont.resume(Result.Error(it))
            }
        }
    }
}