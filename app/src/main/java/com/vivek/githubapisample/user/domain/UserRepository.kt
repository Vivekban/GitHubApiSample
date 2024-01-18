package com.vivek.githubapisample.user.domain

import com.vivek.githubapisample.common.data.AppException
import com.vivek.githubapisample.user.data.User

/**
 * Interface for accessing user information.
 */
interface UserRepository {

    /**
     * Provide user information based on [userName].
     * If no user found for give name then [Result.failure] with [AppException.NotFound]
     *
     * @return Result of User
     */
    suspend fun fetchUserInfo(userName: String): Result<User>

}