package com.vivek.githubapisample.user.data

import retrofit2.Response

/**
 * Performs user related actions like fetching user information
 */
interface UserRemoteSource {

    /**
     * Provide user information based on [username]
     *
     * @return response of [UserDto]
     */
    suspend fun getUserInfo(username: String): Response<UserDto>

}