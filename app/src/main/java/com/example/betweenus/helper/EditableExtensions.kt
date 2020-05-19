package com.example.betweenus.helper

import android.text.Editable

fun Editable?.toStringOrEmptyString(): String {
    return this?.toString() ?: ""
}