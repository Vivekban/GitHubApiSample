package com.vivek.githubapisample.user.data

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Implementation of [UserRemoteSource] which uses retrofit to communicate with server to perform
 * user related actions like fetching user information.
 */
interface UserService : UserRemoteSource {

    /**
     * Fetches user information from server.
     *
     * @param username The username of the user.
     * @return A response containing the user information.
     */
    @GET("users/{username}")
    override suspend fun getUserInfo(
        @Path("username") username: String
    ): Result<UserDto>

    companion object {
        /**
         * Creates a new instance of [UserService] using the given [Retrofit] instance.
         *
         * @param retrofit The retrofit instance to use.
         * @return A new instance of [UserService].
         */
        fun create(retrofit: Retrofit): UserService {
            return retrofit.create(UserService::class.java)
        }
    }

}