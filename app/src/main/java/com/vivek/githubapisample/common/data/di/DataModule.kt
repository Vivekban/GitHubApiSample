package com.vivek.githubapisample.common.data.di

import com.vivek.githubapisample.BuildConfig
import com.vivek.githubapisample.common.data.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Module that provides dependencies for the API.
 *
 * @see SingletonComponent
 */
@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    /**
     * Provides the base URL for the API.
     *
     * @return The base URL.
     */
    @Singleton
    @Provides
    @Named("base_url")
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    /**
     * Provides the API client.
     *
     * @param baseUrl The base URL.
     * @return The API client.
     */
    @Singleton
    @Provides
    fun provideApiClient(@Named("base_url") baseUrl: String): Retrofit {
        return ApiClient.getClient(baseUrl)
    }
}
