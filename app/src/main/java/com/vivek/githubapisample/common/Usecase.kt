package com.vivek.githubapisample.common

/**
 * An interface that represents a use case.
 * It is a function that takes an input and returns an output.
 * Use cases are typically used to perform business logic or data manipulation.
 */
interface SuspendUsecase<Input, out Output> {

    suspend operator fun invoke(params: Input): Output
}