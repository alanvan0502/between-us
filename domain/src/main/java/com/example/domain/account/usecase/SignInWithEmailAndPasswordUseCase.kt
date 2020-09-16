package com.example.domain.account.usecase

import com.example.domain.account.data.SignInData
import com.example.domain.base.SuspendUseCase
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher

class SignInWithEmailAndPasswordUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val accountRepository: AccountRepository
) : SuspendUseCase<SignInData, Unit>(coroutineDispatcher) {

    override suspend fun execute(params: SignInData) {
        accountRepository.signIn(params)
    }
}
