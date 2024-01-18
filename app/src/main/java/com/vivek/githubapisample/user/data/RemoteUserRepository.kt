package com.vivek.githubapisample.user.data

import com.vivek.githubapisample.common.data.AppResult
import com.vivek.githubapisample.user.domain.UserRepository
import javax.inject.Inject

/**
 * Implementation of [UserRepository] uses remote source to fetch User information
 */
class RemoteUserRepository @Inject constructor(private val remote: UserRemoteSource) :
    UserRepository {

    /**
     * Fetches user information from the remote source.
     *
     * @param userName The user name to fetch information for.
     * @return A [AppResult] object containing the user information or an error.
     */
    override suspend fun fetchUserInfo(userName: String): Result<User> {
        return remote.getUserInfo(userName).map { it.toModel() }

    }

}