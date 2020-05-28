package com.example.repository.helper

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

fun FirebaseStorage.profilePicStorageRef(userId: String): StorageReference {
    return reference.child("$userId/profile.jpg")
}