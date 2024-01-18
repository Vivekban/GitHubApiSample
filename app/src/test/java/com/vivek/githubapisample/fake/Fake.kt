package com.vivek.githubapisample.fake

import com.vivek.githubapisample.repo.data.RepoDto
import com.vivek.githubapisample.user.data.UserDto

object Fake {

    fun repoDto(description: String? = "This is temporary fake description") = RepoDto(
        name = "Fake",
        description = description,
        forks = 20,
        updatedAt = null,
        owner = UserDto.fake()
    )
}