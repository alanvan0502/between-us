package com.example.domain.account.usecase

import com.example.domain.base.SuspendUseCase
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher

class UploadProfilePicUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val accountRepository: AccountRepository
) : SuspendUseCase<String, Unit>(coroutineDispatcher) {

    override suspend fun execute(params: String) {
        accountRepository.storageDeleteProfilePic()
        accountRepository.storageUploadProfilePic(params)
    }
}