package com.example.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(params: P): Flow<BUResult<R>> {
        return execute(params)
            .catch { e -> emit(BUResult.Error(e)) }
            .flowOn(coroutineDispatcher)
    }

    abstract fun execute(params: P): Flow<BUResult<R>>
}