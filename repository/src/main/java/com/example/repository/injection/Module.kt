package com.example.repository.injection

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

object Module {

    fun get() = module {
        single { Firebase.auth }
        single { Firebase.firestore }
    }

}