package com.vivek.githubapisample.repo.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vivek.githubapisample.user.data.User

@JsonClass(generateAdapter = true)
data class Repo(

    @Json(name = "name")
    val name: String,

    @Json(name = "owner")
    val owner: User,

    @Json(name = "description")
    val description: String?,

    @Json(name = "forks")
    val forks: Int?,

    @Json(name = "updated_at")
    val updatedAt: String?
) {
    var displayDescription = description ?: ""

    companion object {
        fun fake() = Repo(
            name = "Fake",
            description = "This is temporary fake description",
            forks = 20,
            updatedAt = null,
            owner = User.fake()
        )
    }
}