package com.vivek.githubapisample.common.data

/** A sealed wrapper class that represents an application exception. */
/**
 * A sealed class that represents an application exception.
 */
sealed class AppException(message: String? = null, throwable: Throwable? = null) :
    Exception(message, throwable) {

    /** Represents a general/unknown exception like json parsing. */
    data class Error(override val message: String?, val throwable: Throwable? = null) :
        AppException(message, throwable)

    /** Represents a 404 NotFound response. */
    data class NotFound(val throwable: Throwable? = null) : AppException(null, throwable)

    /** Represents a case when Response is successful but the body is null*/
    data class EmptyBody(val throwable: Throwable? = null) : AppException(null, throwable)

    /** Represents non-2xx responses, contains the error body and the response status code. */
    data class ApiError(
        override val message: String?,
        val code: Int,
        val throwable: Throwable? = null
    ) : AppException(message, throwable)

    /** Represents a NoNetwork exception. */
    data class NetworkError(val throwable: Throwable? = null) : AppException(null, throwable)

}
