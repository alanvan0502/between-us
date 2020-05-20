package com.example.domain.account.usecase

import com.example.domain.account.data.User
import com.example.domain.base.FlowUseCase
import com.example.domain.base.Result
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveAuthUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val accountRepository: AccountRepository
) : FlowUseCase<Any, User?>(coroutineDispatcher) {

    override fun execute(params: Any): Flow<Result<User?>> {
        return accountRepository.observeAuthStatus().map {
            Result.Success(it)
        }
    }

}