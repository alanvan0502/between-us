package com.example.betweenus.managers

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.util.Timer
import java.util.TimerTask

class BackPressManager : LifecycleObserver {

    private var backPressCount = 0

    companion object {
        const val DURATION = 3000L
    }

    fun initialize(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        backPressCount = 0
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        backPressCount = 0
    }

    fun backPress(firstBackPressAction: () -> Unit, secondBackPressAction: () -> Unit) {
        if (backPressCount == 0) {
            backPressCount += 1
            val timer = Timer()
            timer.schedule(
                object : TimerTask() {
                    override fun run() {
                        backPressCount = 0
                    }
                },
                DURATION
            )
            firstBackPressAction()
        } else {
            backPressCount = 0
            secondBackPressAction()
        }
    }
}
