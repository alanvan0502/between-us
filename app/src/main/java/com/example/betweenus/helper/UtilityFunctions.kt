package com.example.betweenus.helper

inline fun tryLazy(safeCall: () -> Unit) {
    try {
        safeCall()
    } catch (e: Exception) {
    }
}