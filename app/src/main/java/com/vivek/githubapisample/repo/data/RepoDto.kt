package com.vivek.githubapisample.repo.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vivek.githubapisample.user.data.UserDto

/**
 * Represents a Repo object that receives from api/cloud
 */
@JsonClass(generateAdapter = true)
data class RepoDto(

    @Json(name = "name")
    val name: String,

    @Json(name = "owner")
    val owner: UserDto,

    @Json(name = "description")
    val description: String?,

    @Json(name = "forks")
    val forks: Int?,

    @Json(name = "updated_at")
    val updatedAt: String?
)