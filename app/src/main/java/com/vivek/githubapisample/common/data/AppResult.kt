package com.vivek.githubapisample.common.data

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * A generic class that holds a value with its loading status.
 */
sealed class AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>()

    @Immutable
    data class Error(val exception: Throwable? = null) : AppResult<Nothing>()
    data object Loading : AppResult<Nothing>()

    /**
     * Returns `true` if this instance represents a successful outcome.
     */
    fun isSuccess(): Boolean = this is Success

    /**
     * Returns `true` if this instance represents a failed outcome.
     */
    fun isError(): Boolean = this is Error

    /**
     * Returns `true` if this instance represents a failed outcome.
     */
    fun isLoading(): Boolean = this is Loading

    // value & exception retrieval

    /**
     * Returns the encapsulated value if this instance represents [success][AppResult.isSuccess] or `null`
     * if it is [failure][AppResult.isError].
     */

    fun getOrNull(): T? =
        when (this) {
            is Success -> this.data
            else -> null
        }

    /**
     * Returns the encapsulated [Throwable] exception if this instance represents [failure][isError] or `null`
     * if it is [success][isSuccess].
     */
    fun exceptionOrNull(): Throwable? =
        when (this) {
            is Error -> this.exception
            else -> null
        }
}

fun <T> Flow<T>.asResult(): Flow<AppResult<T>> {
    return this
        .map<T, AppResult<T>> {
            AppResult.Success(it)
        }
        .onStart { emit(AppResult.Loading) }
        .catch { emit(AppResult.Error(it)) }
}
