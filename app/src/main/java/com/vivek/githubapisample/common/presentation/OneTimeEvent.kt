package com.vivek.githubapisample.common.presentation

/**
 * This is typically used for one-time events like navigation and showing Snackbar messages.
 * Once its handled, it will not be handled again.
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