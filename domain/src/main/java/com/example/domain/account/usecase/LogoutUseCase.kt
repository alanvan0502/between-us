package com.example.domain.account.usecase

import com.example.domain.base.SuspendUseCase
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher

class LogoutUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val accountRepository: AccountRepository
) : SuspendUseCase<Any, Unit>(coroutineDispatcher) {

    override suspend fun execute(params: Any) {
        accountRepository.logout()
    }
}