package com.vivek.githubapisample.common.presentation.helper

import com.vivek.githubapisample.R
import com.vivek.githubapisample.common.data.AppException
import com.vivek.githubapisample.common.presentation.UiString
import com.vivek.githubapisample.common.presentation.uiString

/**
 * Converts a Throwable to a [UiString].
 *
 * @return parse error and based on that UI String.
 */
fun Throwable.uiString(): UiString = when (this) {
    is AppException -> when (this) {
        is AppException.Error -> message?.uiString
        is AppException.NetworkError -> R.string.error_no_network.uiString
        is AppException.NotFound -> R.string.error_not_found.uiString
        is AppException.ApiError -> "${message?.uiString} $code".uiString
        is AppException.EmptyBody -> R.string.empty_response.uiString
    } ?: R.string.error_unknown.uiString

    else -> message?.uiString ?: R.string.error_unknown.uiString
}
