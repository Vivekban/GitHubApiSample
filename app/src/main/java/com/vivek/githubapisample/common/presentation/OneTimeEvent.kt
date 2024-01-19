package com.vivek.githubapisample.common.presentation

/**
 * A class used for one-time handling of events like navigation and showing Snackbar message.
 * Although after first time content will be null, actual content can be accessed via [peekContent].
 */
open class OneTimeEvent<out T>(private val content: T) {

    private var _hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContent(): T? {
        return if (_hasBeenHandled) {
            null
        } else {
            _hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    @Suppress("unused")
    fun peekContent(): T = content
}