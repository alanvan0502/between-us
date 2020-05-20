package com.example.betweenus.injection

import com.example.betweenus.main.MainViewModel
import com.example.betweenus.user_account.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    fun get() = module {
        viewModel { LoginViewModel() }
        viewModel { MainViewModel() }
    }
}