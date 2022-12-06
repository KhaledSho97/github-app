package com.khaledsh.domain.usecases.base

/**
 * Base use case for use cases that don't require any network calls
 */
abstract class BaseUseCase<T, in P : UseCaseParameters> {
    abstract operator fun invoke(params: P): T
}

