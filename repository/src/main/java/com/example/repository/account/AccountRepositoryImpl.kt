package com.example.repository.account

import com.example.domain.account.data.AuthData
import com.example.domain.account.data.SignInData
import com.example.domain.account.data.User
import com.example.domain.repository.AccountRepository
import com.example.repository.account.data.mapper.UserMapper
import com.example.repository.helper.CollectionPath
import com.example.repository.helper.collection
import com.example.repository.helper.profilePicStorageRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageException.ERROR_OBJECT_NOT_FOUND
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.java.KoinJavaComponent.inject
import java.io.File
import java.io.FileInputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AccountRepositoryImpl : AccountRepository {

    private val auth by inject(FirebaseAuth::class.java)
    private val fireStore by inject(FirebaseFirestore::class.java)
    private val storage by inject(FirebaseStorage::class.java)

    override fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun storageUploadProfilePic(pictureAbsPath: String) {
        return suspendCancellableCoroutine { cont ->
            if (auth.currentUser?.uid != null) {
                val storageRef = storage.profilePicStorageRef(auth.currentUser?.uid!!)
                val uploadTask = storageRef.putStream(FileInputStream(File(pictureAbsPath)))
                uploadTask.addOnSuccessListener {
                    cont.resume(Unit)
                }.addOnFailureListener {
                    cont.resumeWithException(it)
                }
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun storageGetProfilePicUrl(): String? {
        return suspendCancellableCoroutine { cont ->
            if (auth.currentUser?.uid != null) {
                val storageRef = storage.profilePicStorageRef(auth.currentUser?.uid!!)
                storageRef.downloadUrl.addOnSuccessListener {
                    cont.resume(it.toString())
                }.addOnFailureListener {
                    cont.resumeWithException(it)
                }
            } else {
                cont.resume(null)
            }
        }
    }

    override suspend fun storageDeleteProfilePic() {
        return suspendCancellableCoroutine { cont ->
            if (auth.currentUser?.uid != null) {
                val storageRef = storage.profilePicStorageRef(auth.currentUser?.uid!!)
                storageRef.delete().addOnSuccessListener {
                    cont.resume(Unit)
                }.addOnFailureListener { exception ->
                    val errorCode = (exception as? StorageException)?.errorCode
                    if (errorCode == ERROR_OBJECT_NOT_FOUND) {
                        cont.resume(Unit)
                    } else {
                        cont.resumeWithException(exception)
                    }
                }
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun fireStoreSaveUser(user: User) {
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

    override suspend fun authCreateUser(email: String, password: String) {
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

    override suspend fun authUpdateUser(email: String?, password: String?) {
        email?.let { updateEmail(it) }
        password?.let { updatePassword(it) }
    }

    private suspend fun updatePassword(password: String) {
        return suspendCancellableCoroutine { cont ->
            auth.currentUser?.updatePassword(password)?.addOnSuccessListener {
                cont.resume(Unit)
            }?.addOnFailureListener {
                cont.resumeWithException(it)
            }
        }
    }

    private suspend fun updateEmail(email: String) {
        return suspendCancellableCoroutine { cont ->
            auth.currentUser?.updateEmail(email)?.addOnSuccessListener {
                cont.resume(Unit)
            }?.addOnFailureListener {
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
        val document =
            fireStore.collection(CollectionPath.USERS).document(auth.currentUser?.uid!!)
        return callbackFlow {
            val registration = document.addSnapshotListener { snapShot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                try {
                    offer(snapShot?.toObject(User::class.java))
                } catch (e: Throwable) {
                    close(e)
                    return@addSnapshotListener
                }
            }
            awaitClose {
                registration.remove()
            }
        }
    }
}
