package com.example.betweenus.helper

fun tryLazy(safeCall: () -> Unit) {
    try {
        safeCall()
    } catch (e: Exception) {
    }
}