package com.example.betweenus.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.inputmethod.InputMethodManager

fun Activity.hideSoftInputKeyboard(windowToken: IBinder? = null, flags: Int = 0) {
    val inputMethodManger = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    if (windowToken != null) {
        inputMethodManger?.hideSoftInputFromWindow(windowToken, flags)
    } else {
        currentFocus?.windowToken?.let {
            inputMethodManger?.hideSoftInputFromWindow(it, flags)
        }
    }
}

inline fun <reified T> Activity.start(finishCurrent: Boolean = false) {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
    if (finishCurrent) {
        finish()
    }
}