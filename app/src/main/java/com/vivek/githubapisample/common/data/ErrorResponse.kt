package com.vivek.githubapisample.common.data

import com.squareup.moshi.JsonClass

/**
 * Error response comes in 4xx related api errors
 */
@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val message: String,
)