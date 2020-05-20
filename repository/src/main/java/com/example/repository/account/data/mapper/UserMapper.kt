package com.example.repository.account.data.mapper

import com.example.domain.account.data.User
import com.example.domain.base.DataMapper
import com.google.firebase.auth.FirebaseUser

class UserMapper: DataMapper<FirebaseUser, User>() {
    override fun map(source: FirebaseUser): User {
        return User(
            uid = source.uid,
            displayName = source.displayName,
            email = source.email,
            photoUrl = source.photoUrl.toString(),
            phoneNumber = source.phoneNumber
        )
    }
}