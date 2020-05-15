package com.example.betweenus

import android.app.Application
import com.example.betweenus.injection.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("unused")
class BetweenUsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            androidLogger()
            androidContext(this@BetweenUsApp)
            modules(AppModule.get())
        }
    }

}