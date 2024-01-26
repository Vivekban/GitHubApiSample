package com.vivek.githubapisample.user.data

import androidx.compose.runtime.Immutable

/**
 * A business data object that represents a User this object wil be available in domain/presentation layer
 * It act act as bridge between data layer and domain layer
 */
@Immutable
data class User(
    val id: Int,

    /** Name of user */
    val name: String?,

    /** Avatar url of user */
    val avatarUrl: String?,

    /** Login name of the user. Used for fetching particular repo detail */
    val login: String? = null
) {
    /**
     * Returns non null name of user.
     */
    val displayName: String = name ?: "Unknown"

    companion object {
        /**
         * Fake user for testing and UI preview
         */
        fun fake() = User(
            id = 1,
            name = "Fake",
            avatarUrl = "https://avatars3.githubusercontent.com/u/583231?v=4",
            login = "Fake"
        )
    }
}