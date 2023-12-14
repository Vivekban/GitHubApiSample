package com.vivek.githubapisample.common.presentation

/**
 * Used as a wrapper for data that is exposed via a flow/livedata that represents an event.
 * This is typically used for one-time events like navigation and showing Snackbar messages.
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
    fun peekContent(): T = content
}