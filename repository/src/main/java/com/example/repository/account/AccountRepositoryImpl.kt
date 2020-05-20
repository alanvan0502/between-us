package com.example.repository.account

import com.example.domain.account.data.SignInData
import com.example.domain.account.data.User
import com.example.domain.repository.AccountRepository
import com.example.repository.account.data.mapper.UserMapper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@ExperimentalCoroutinesApi
class AccountRepositoryImpl : AccountRepository {

    private val auth by inject(FirebaseAuth::class.java)

    override fun isUserSignedIn(): Boolean {
        auth.currentUser
        return auth.currentUser != null
    }

    override suspend fun signIn(data: SignInData) {
        return suspendCancellableCoroutine { cont ->
            auth.signInWithEmailAndPassword(
                data.email, data.password
            ).addOnSuccessListener {
                cont.resume(Unit)
            }.addOnFailureListener {
                cont.resumeWithException(it)
            }
        }
    }

    override fun logout() {
        auth.signOut()
    }

    override fun observeAuthStatus(): Flow<User?> {
        return auth.observeAuthStatus()
    }

    private fun FirebaseAuth.observeAuthStatus(): Flow<User?> {
        var producerScope: ProducerScope<User?>? = null
        val authStateListener = FirebaseAuth.AuthStateListener {
            val currentUser = it.currentUser
            producerScope?.apply {
                offer(UserMapper().map(currentUser))
            }
        }
        return callbackFlow {
            producerScope = this
            this@observeAuthStatus.addAuthStateListener(authStateListener)
            awaitClose {
                this@observeAuthStatus.removeAuthStateListener(authStateListener)
            }
        }
    }
}