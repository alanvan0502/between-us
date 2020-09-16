package com.example.betweenus.helper

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View

fun View.increaseTouchableArea(extraSpace: Int = 50) {
    val parent = this.parent as android.view.View
    parent.post {
        tryLazy {
            val touchableArea = Rect()
            this.getHitRect(touchableArea)
            touchableArea.top -= extraSpace
            touchableArea.bottom += extraSpace
            touchableArea.left -= extraSpace
            touchableArea.right += extraSpace
            parent.touchDelegate = TouchDelegate(touchableArea, this)
        }
    }
}

val View.isActivityDestroyed get() = context.activity?.isDestroyed != false

fun View.removeOnClickListener() {
    setOnClickListener {
    }
    isClickable = false
}
