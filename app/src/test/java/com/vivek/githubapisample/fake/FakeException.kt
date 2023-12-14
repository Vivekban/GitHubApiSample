package com.vivek.githubapisample.fake

import com.vivek.githubapisample.user.data.User
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

object FakeException {

    val noConnection = HttpException(
        Response.error<User>(
            500,
            "".toResponseBody()
        )
    )
}