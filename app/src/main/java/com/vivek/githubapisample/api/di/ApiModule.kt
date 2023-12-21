package com.vivek.githubapisample.api.di

import com.vivek.githubapisample.api.ApiClient
import com.vivek.githubapisample.common.data.DataConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Module that provides dependencies for the API.
 *
 * @see SingletonComponent
 */
@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    /**
     * Provides the base URL for the API.
     *
     * @return The base URL.
     */
    @Singleton
    @Provides
    fun provideBaseUrl(): String = DataConstant.BASE_URL

    /**
     * Provides the API client.
     *
     * @param baseUrl The base URL.
     * @return The API client.
     */
    @Singleton
    @Provides
    fun provideApiClient(baseUrl: String): Retrofit {
        return ApiClient.getClient(baseUrl)
    }

}
