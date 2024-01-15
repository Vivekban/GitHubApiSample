package com.vivek.githubapisample.common.presentation.helper

import com.vivek.githubapisample.R
import com.vivek.githubapisample.api.AppException
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
        is AppException.NoNetwork -> R.string.error_no_network.uiString
        is AppException.NotFound -> R.string.error_not_found.uiString
    } ?: R.string.error_unknown.uiString

    else -> message?.uiString ?: R.string.error_unknown.uiString
}
