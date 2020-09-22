package com.example.betweenus

import android.app.Application
import com.example.betweenus.injection.AppModule
import com.example.betweenus.injection.DomainModule
import com.example.betweenus.injection.RepositoryModule
import com.example.repository.RepositoryApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("unused")
class BetweenUsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
        initializeModules()
    }

    private fun initializeKoin() {
        startKoin {
            androidLogger()
            androidContext(this@BetweenUsApp)
            modules(
                AppModule.get(),
                RepositoryModule.get(),
                DomainModule.get()
            )
        }
    }

    private fun initializeModules() {
        RepositoryApp.initialize()
    }
}
