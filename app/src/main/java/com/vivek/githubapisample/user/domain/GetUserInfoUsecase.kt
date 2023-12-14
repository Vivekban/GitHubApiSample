package com.vivek.githubapisample.user.domain

import com.vivek.githubapisample.common.AppResult
import com.vivek.githubapisample.common.SuspendUsecase
import com.vivek.githubapisample.user.data.User
import javax.inject.Inject

class GetUserInfoUsecase @Inject constructor(private val userRepository: UserRepository) :
    SuspendUsecase<GetUserInfoUsecase.Param, AppResult<User>> {

    data class Param(val userName: String)

    override suspend fun invoke(params: Param): AppResult<User> {
        return userRepository.fetchUserInfo(params.userName)
    }

}