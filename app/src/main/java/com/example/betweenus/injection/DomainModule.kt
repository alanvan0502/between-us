package com.example.betweenus.injection

import com.example.domain.account.usecase.*
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

object DomainModule {

    fun get() = module {
        factory { SignInWithEmailAndPasswordUseCase(Dispatchers.IO, get()) }
        factory { ObserveAuthUseCase(Dispatchers.Main, get()) }
        factory { LogoutUseCase(Dispatchers.IO, get()) }
        factory { SignUpUseCase(Dispatchers.IO, get()) }
        factory { ObserveUserUseCase(Dispatchers.Main, get()) }
        factory { UploadProfilePicUseCase(Dispatchers.IO, get()) }
        factory { UpdateUserUseCase(Dispatchers.IO, get()) }
    }
}