package com.example.betweenus.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

fun TextView.addSimpleTextChangedListener(afterTextChange: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChange(s.toStringOrEmptyString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}