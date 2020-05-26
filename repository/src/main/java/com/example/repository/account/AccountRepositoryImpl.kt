package com.example.repository.account

import com.example.domain.account.data.AuthData
import com.example.domain.account.data.SignInData
import com.example.domain.account.data.SignUpData
import com.example.domain.account.data.User
import com.example.domain.repository.AccountRepository
import com.example.repository.account.data.mapper.UserMapper
import com.example.repository.helper.CollectionPath
import com.example.repository.helper.collection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@ExperimentalCoroutinesApi
class AccountRepositoryImpl : AccountRepository {

    private val auth by inject(FirebaseAuth::class.java)
    private val fireStore by inject(FirebaseFirestore::class.java)

    override fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun signUp(data: SignUpData) {
        val email = data.user.email ?: return
        createUser(email, data.password)
        saveUserToFireStore(data.user)
    }

    private suspend fun saveUserToFireStore(user: User) {
        return suspendCancellableCoroutine { cont ->
            if (auth.currentUser?.uid != null) {
                fireStore.collection(CollectionPath.USERS)
                    .document(auth.currentUser?.uid!!)
                    .set(user)
                    .addOnSuccessListener {
                        cont.resume(Unit)
                    }.addOnFailureListener {
                        cont.resumeWithException(it)
                    }
            } else {
                cont.resume(Unit)
            }
        }
    }

    private suspend fun createUser(email: String, password: String) {
        return suspendCancellableCoroutine { cont ->
            auth.createUserWithEmailAndPassword(
                email, password
            ).addOnSuccessListener {
                cont.resume(Unit)
            }.addOnFailureListener {
                cont.resumeWithException(it)
            }
        }
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

    override fun observeAuthStatus(): Flow<AuthData?> {
        return auth.observeAuthStatus()
    }

    private fun FirebaseAuth.observeAuthStatus(): Flow<AuthData?> {
        var producerScope: ProducerScope<AuthData?>? = null
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

    override fun observeUser(): Flow<User?> {
        val document = fireStore.collection(CollectionPath.USERS).document(auth.currentUser?.uid!!)
        return callbackFlow {
            document.addSnapshotListener { snapShot, exception ->
                val user = try {
                    snapShot?.toObject(User::class.java)
                } catch (e: Throwable) {
                    null
                }
                if (user == null || exception != null) {
                    close()
                } else {
                    offer(user)
                }
            }
            awaitClose { }
        }
    }
}