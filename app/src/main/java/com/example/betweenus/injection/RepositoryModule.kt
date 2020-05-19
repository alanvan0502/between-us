package com.example.betweenus.injection

import com.example.domain.repository.AccountRepository
import com.example.repository.account.AccountRepositoryImpl
import org.koin.dsl.module

object RepositoryModule {

    fun get() = module {
        single<AccountRepository> {
            AccountRepositoryImpl()
        }
    }
}