package com.vivek.githubapisample.api

/**
 * A sealed class that represents an application exception.
 */
sealed class AppException(message: String?, throwable: Throwable?) : Exception(message, throwable) {
    /** A data class that represents a NotFound exception. */
    data class NotFound(val throwable: Throwable? = null) : AppException("Not found", throwable)

    /** A data class that represents a NoNetwork exception. */
    data class NoNetwork(val throwable: Throwable? = null) : AppException("No Network", throwable)

    /** A data class that represents an Unknown exception. */
    data class Unknown(val throwable: Throwable? = null) : AppException("Unknown", throwable)

}