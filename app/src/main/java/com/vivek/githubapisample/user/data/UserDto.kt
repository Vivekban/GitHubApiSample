package com.vivek.githubapisample.user.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Represents a user object receives from cloud
 */
@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String?,

    @Json(name = "avatar_url")
    val avatarUrl: String?,

    @Json(name = "login")
    val login: String? = null
) {
    companion object {
        /**
         * Fake user for testing and UI preview
         */
        fun fake() = UserDto(
            id = 1,
            name = "Fake",
            avatarUrl = "https://avatars3.githubusercontent.com/u/583231?v=4",
            login = "Fake"
        )
    }
}