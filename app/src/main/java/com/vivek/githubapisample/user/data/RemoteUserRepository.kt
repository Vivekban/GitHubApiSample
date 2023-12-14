package com.vivek.githubapisample.user.data

import com.vivek.githubapisample.api.AppException
import com.vivek.githubapisample.common.AppResult
import com.vivek.githubapisample.user.domain.UserRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Implementation of [UserRepository] uses remote source to fetch User information
 */
class RemoteUserRepository @Inject constructor(private val userService: UserService) :
    UserRepository {

    /**
     * Fetches user information from the remote source.
     *
     * @param userName The user name to fetch information for.
     * @return A [AppResult] object containing the user information or an error.
     */
    override suspend fun fetchUserInfo(userName: String): AppResult<User> {
        return try {
            val userResponse = userService.getUserInfo(userName)

            userResponse.body()?.let { user -> AppResult.Success(user) }
                ?: AppResult.Error(AppException.NotFound())

        } catch (e: HttpException) {
            AppResult.Error(AppException.Unknown())
        } catch (e: IOException) {
            AppResult.Error(AppException.NoNetwork())
        }
    }

}