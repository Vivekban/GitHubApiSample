package com.vivek.githubapisample.repo.data

import com.vivek.githubapisample.user.data.toModel

/**
 * Converts [RepoDto] to [Repo]
 */
fun RepoDto.toModel() = Repo(
    name = name,
    description = description,
    forks = forks,
    updatedAt = updatedAt,
    owner = owner.toModel()
)

