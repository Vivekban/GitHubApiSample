package com.vivek.githubapisample.ui.common

import com.vivek.githubapisample.R
import com.vivek.githubapisample.api.AppException

/**
 * Converts a Throwable to a user-friendly message.
 *
 * @return The resource id of the user-friendly message.
 */
fun Throwable.toUserFriendlyMessage(): Int {

    if (this is AppException) {

        return when (this) {
            is AppException.NotFound -> R.string.error_not_found
            is AppException.Unknown -> R.string.error_unknown
            is AppException.NoNetwork -> R.string.error_no_network
        }
    }
    return R.string.error_unknown
}
