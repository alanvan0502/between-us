package com.example.repository.account.data.mapper

import com.example.domain.account.data.AuthData
import com.example.domain.base.DataMapper
import com.google.firebase.auth.FirebaseUser

class UserMapper : DataMapper<FirebaseUser?, AuthData?>() {
    override fun map(source: FirebaseUser?): AuthData? {
        source ?: return null
        return AuthData(uid = source.uid)
    }
}