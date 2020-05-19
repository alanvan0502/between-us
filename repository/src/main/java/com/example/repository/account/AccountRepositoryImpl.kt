package com.example.repository.account

import com.example.domain.account.data.SignInData
import com.example.domain.account.data.User
import com.example.domain.repository.AccountRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@FlowPreview
@ExperimentalCoroutinesApi
class AccountRepositoryImpl : AccountRepository {

    private val auth by inject(FirebaseAuth::class.java)

    private val channel = ConflatedBroadcastChannel<User>()

    private val authStateListener = FirebaseAuth.AuthStateListener {
        val currentUser = it.currentUser
        if (!channel.isClosedForSend) {
            channel.offer(
                User(
                    uid = currentUser?.uid,
                    displayName = currentUser?.displayName,
                    email = currentUser?.email,
                    photoUrl = currentUser?.photoUrl.toString(),
                    phoneNumber = currentUser?.phoneNumber
                )
            )
        } else {
            unregisterListener()
        }
    }

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

    override fun observeAuthStatus(): Flow<User> {
        auth.addAuthStateListener(authStateListener)
        return channel.asFlow()
    }

    private fun unregisterListener() {
        auth.removeAuthStateListener(authStateListener)
    }
}