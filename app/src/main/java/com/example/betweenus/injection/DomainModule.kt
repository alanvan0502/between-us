package com.example.betweenus.injection

import com.example.domain.account.usecase.LogoutUseCase
import com.example.domain.account.usecase.ObserveAuthUseCase
import com.example.domain.account.usecase.SignInWithEmailAndPasswordUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

object DomainModule {

    fun get() = module {
        factory { SignInWithEmailAndPasswordUseCase(Dispatchers.IO, get()) }
        factory { ObserveAuthUseCase(Dispatchers.Main, get()) }
        factory { LogoutUseCase(Dispatchers.IO, get()) }
    }
}