package com.vivek.githubapisample.user.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Performs user related actions like fetching user information
 */
interface UserService : UserRemoteSource {

    /**
     * Provide user information based on [username]
     *
     * @return response of [User]
     */
    @GET("users/{username}")
    override suspend fun getUserInfo(
        @Path("username") username: String
    ): Response<UserDto>

    companion object {
        fun create(retrofit: Retrofit): UserService {
            return retrofit.create(UserService::class.java)
        }
    }

}