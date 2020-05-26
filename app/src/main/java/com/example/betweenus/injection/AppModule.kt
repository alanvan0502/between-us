package com.example.betweenus.injection

import com.example.betweenus.common.BackPressManager
import com.example.betweenus.main.MainViewModel
import com.example.betweenus.user_account.login.LoginViewModel
import com.example.betweenus.user_account.sign_up.SignUpViewModel
import com.example.betweenus.user_account.user_profile.UserProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    fun get() = module {
        viewModel { LoginViewModel() }
        viewModel { MainViewModel() }
        viewModel { SignUpViewModel() }
        viewModel { UserProfileViewModel() }
        factory { BackPressManager() }
    }
}