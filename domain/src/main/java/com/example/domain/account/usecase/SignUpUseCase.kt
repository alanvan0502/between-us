package com.example.domain.account.usecase

import com.example.domain.account.data.SignUpData
import com.example.domain.base.SuspendUseCase
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher

class SignUpUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val accountRepository: AccountRepository
) : SuspendUseCase<SignUpData, Unit>(coroutineDispatcher) {

    override suspend fun execute(params: SignUpData) {
        val email = params.user.email ?: return
        accountRepository.authCreateUser(email, params.password)
        accountRepository.fireStoreSaveUser(params.user)
    }
}
