package com.khaledsh.domain.usecases.base

import com.khaledsh.domain.models.Result
import com.khaledsh.domain.models.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Base use case that wraps [suspending][suspend] [run] function with [flow][Flow] and returns it for later usage.
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseNetworkUseCase<T, in P : UseCaseParameters> {

    abstract suspend fun FlowCollector<Result<T>>.run(params: P)

    operator fun invoke(params: P) =
        flow {
            emit(State.Loading)
            run(params)
            emit(State.Loaded)
        }.flowOn(Dispatchers.IO)
}


