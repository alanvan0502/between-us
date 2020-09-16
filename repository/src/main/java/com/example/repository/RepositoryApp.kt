package com.example.repository

import android.app.Application
import com.example.repository.injection.Module
import org.koin.core.context.loadKoinModules

class RepositoryApp : Application() {
    companion object {
        fun initialize() {
            loadKoinModules(Module.get())
        }
    }
}
