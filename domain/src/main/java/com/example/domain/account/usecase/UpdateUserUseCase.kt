package com.example.domain.account.usecase

import com.example.domain.account.data.User
import com.example.domain.base.SuspendUseCase
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher

class UpdateUserUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val accountRepository: AccountRepository
) : SuspendUseCase<UpdateUserUseCase.Params, Unit>(coroutineDispatcher) {

    override suspend fun execute(params: Params) {
        val photoUrl = accountRepository.storageGetProfilePicUrl()
        accountRepository.fireStoreSaveUser(
            User(
                name = params.name,
                email = params.email,
                photoUrl = photoUrl
            )
        )
        accountRepository.authUpdateUser(email = params.email)
    }

    data class Params(
        val name: String = "",
        val email: String = "",
        val phoneNumber: String = ""
    )
}
