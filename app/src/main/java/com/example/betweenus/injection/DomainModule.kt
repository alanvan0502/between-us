package com.example.betweenus.injection

import com.example.domain.account.usecase.SignInWithEmailAndPasswordUseCase
import com.example.domain.account.usecase.ObserveAuthUseCase
import com.example.domain.account.usecase.LogoutUseCase
import com.example.domain.account.usecase.SignUpUseCase
import com.example.domain.account.usecase.ObserveUserUseCase
import com.example.domain.account.usecase.UploadProfilePicUseCase
import com.example.domain.account.usecase.UpdateUserUseCase
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
