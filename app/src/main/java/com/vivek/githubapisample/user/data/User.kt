package com.vivek.githubapisample.user.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Represents a user
 */
@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id")
    val id: Int,

    /**
     * Name of user
     */
    @Json(name = "name")
    val name: String?,

    /**
     * Avatar url of user
     */
    @Json(name = "avatar_url")
    val avatarUrl: String?,

    /**
     * Login name of the user. Used for fetching particular repo detail
     */
    @Json(name = "login")
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