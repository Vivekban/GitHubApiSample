package com.vivek.githubapisample.user.data

/**
 * It converts [UserDto] to [User]
 */
fun UserDto.toModel() = User(id, name, avatarUrl, login)

