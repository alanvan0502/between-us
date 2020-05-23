package com.example.betweenus.helper

import android.text.Editable
import com.google.android.material.textfield.TextInputLayout

fun Editable?.toStringOrEmptyString(): String {
    return this?.toString() ?: ""
}

fun TextInputLayout.verify(errorPredicate: Boolean, errorMessage: String) {
    error = if (errorPredicate) {
        errorMessage
    } else {
        null
    }
}