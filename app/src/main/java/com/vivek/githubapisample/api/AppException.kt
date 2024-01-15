package com.vivek.githubapisample.api

/** A sealed wrapper class that represents an application exception. */
/**
 * A sealed class that represents an application exception.
 */
sealed class AppException(message: String? = null, throwable: Throwable? = null) :
    Exception(message, throwable) {

    /** A data class that represents a General exception. */
    data class Error(val reason: String?, val throwable: Throwable? = null) :
        AppException(reason, throwable)

    /** A data class that represents a NotFound exception. */
    data class NotFound(val throwable: Throwable? = null) : AppException("Not found", throwable)

    /** A data class that represents a NoNetwork exception. */
    data class NoNetwork(val throwable: Throwable? = null) : AppException("No Network", throwable)
}
