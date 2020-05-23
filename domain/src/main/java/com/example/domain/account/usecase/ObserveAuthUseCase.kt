package com.example.domain.account.usecase

import com.example.domain.account.data.AuthData
import com.example.domain.base.FlowUseCase
import com.example.domain.base.BUResult
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveAuthUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val accountRepository: AccountRepository
) : FlowUseCase<Any, AuthData?>(coroutineDispatcher) {

    override fun execute(params: Any): Flow<BUResult<AuthData?>> {
        return accountRepository.observeAuthStatus().map {
            BUResult.Success(it)
        }
    }

}