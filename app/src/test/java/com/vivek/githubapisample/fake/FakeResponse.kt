package com.vivek.githubapisample.fake

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object FakeResponse {

    fun <T> notFound(): Response<T> = Response.error(
        404,
        "Not found".toResponseBody()
    )

}