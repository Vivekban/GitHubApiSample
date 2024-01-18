package com.vivek.githubapisample.common.data

import com.vivek.githubapisample.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * This class will be responsible to provide all kind Retrofit Client for making networking calls.
 */
object ApiClient {

    /**
     * This method will provide the Retrofit client with the given base url By default it will
     * logs all network calls in debug mode
     *
     * @param baseUrl The base url of the api
     * @return The Retrofit client
     */
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
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()
    }

}
