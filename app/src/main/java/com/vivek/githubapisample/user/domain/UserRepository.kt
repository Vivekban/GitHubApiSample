package com.vivek.githubapisample.user.domain

import com.vivek.githubapisample.api.AppException
import com.vivek.githubapisample.common.data.AppResult
import com.vivek.githubapisample.user.data.User

/**
 * Manages
 */
interface UserRepository {

    /**
     * Provide user information based on [userName].
     * If no user found for give name then [Result.failure] with [AppException.NotFound]
     *
     * @return Result of User
     */
    suspend fun fetchUserInfo(userName: String): AppResult<User>

}