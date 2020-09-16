package com.example.repository.helper

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

// TODO: reconsider naming
enum class CollectionPath(val value: String) {
    USERS("Users"),
    EMAIL("email"),
    PHONE("phone"),
    NAME("name"),
    IMAGE_URL("status"),
    STATUS_URL("statusUrl"),
    STATUS_TIME("statusTime")
}

fun FirebaseFirestore.collection(path: CollectionPath): CollectionReference {
    return this.collection(path.value)
}
