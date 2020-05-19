package com.example.betweenus.injection

import com.example.betweenus.user_account.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    fun get() = module {
        viewModel { LoginViewModel() }

        single(DependencyTag.DISPATCHERS_DEFAULT) { Dispatchers.Default }
        single(DependencyTag.DISPATCHERS_MAIN) { Dispatchers.Main }
        single(DependencyTag.DISPATCHERS_IO) { Dispatchers.IO }
    }
}