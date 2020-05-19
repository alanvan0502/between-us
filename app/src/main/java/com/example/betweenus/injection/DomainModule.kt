package com.example.betweenus.injection

import com.example.domain.account.usecase.SignInWithEmailAndPasswordUseCase
import org.koin.dsl.module

object DomainModule {

    fun get() = module {
        factory { SignInWithEmailAndPasswordUseCase(get(DependencyTag.DISPATCHERS_IO), get()) }
    }
}