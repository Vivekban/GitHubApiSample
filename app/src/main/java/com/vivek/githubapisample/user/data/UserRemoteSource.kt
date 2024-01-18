package com.vivek.githubapisample.user.data

/**
 * Performs user related actions like fetching user information from api
 */
interface UserRemoteSource {

    /**
     * Provide user information based on [username]
     *
     * @return response of [UserDto]
     */
    suspend fun getUserInfo(username: String): Result<UserDto>

}