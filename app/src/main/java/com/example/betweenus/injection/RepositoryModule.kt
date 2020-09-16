package com.example.betweenus.injection

import com.example.domain.repository.AccountRepository
import com.example.repository.account.AccountRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
object RepositoryModule {

    fun get() = module {
        single<AccountRepository> {
            AccountRepositoryImpl()
        }
    }
}
