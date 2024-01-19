package com.vivek.githubapisample.common.domain

/**
 * Use cases are typically used to perform business logic or data manipulation.
 *
 * It takes an [Input] and returns an [Output] in asynchronous way.
 */
interface AsyncUsecase<Input, out Output> {

    suspend operator fun invoke(params: Input): Output
}