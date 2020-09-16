package com.example.domain.account.usecase

import com.example.domain.account.data.User
import com.example.domain.base.BUResult
import com.example.domain.base.FlowUseCase
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveUserUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val accountRepository: AccountRepository
) : FlowUseCase<Any, User>(coroutineDispatcher) {

    override fun execute(params: Any): Flow<BUResult<User>> {
        return accountRepository.observeUser().map {
            BUResult.Success(it)
        }
    }
}
