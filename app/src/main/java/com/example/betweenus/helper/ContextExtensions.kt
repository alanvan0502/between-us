package com.example.betweenus.helper

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

val Context.activity: Activity?
    get() =
        this as? Activity ?: if (this is ContextWrapper) {
            this.baseContext.activity
        } else {
            null
        }