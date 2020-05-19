package com.example.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class SuspendUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(params: P): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(params).let {
                    Result.Success(it)
                }
            }
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    protected abstract suspend fun execute(params: P): R
}