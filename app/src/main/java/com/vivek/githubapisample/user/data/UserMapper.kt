package com.vivek.githubapisample.user.data

/**
 * It converts [UserDto] to [User] basically api object to business object
 */
fun UserDto.toModel() = User(id, name, avatarUrl, login)

