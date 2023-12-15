package com.vivek.githubapisample.api

import com.vivek.githubapisample.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * This class will be responsible to provide all kind Retrofit Client for making networking calls
 * like Authenticated, Unauthenticated etc
 */
class ApiClient {

    companion object {

        fun getClient(baseUrl: String): Retrofit {
            val logger =
                HttpLoggingInterceptor().apply {
                    level = when (BuildConfig.DEBUG) {
                        true -> HttpLoggingInterceptor.Level.BODY
                        false -> HttpLoggingInterceptor.Level.NONE
                    }
                }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create()).build()
        }
    }
}
