package com.vivek.githubapisample.api.di

import com.vivek.githubapisample.api.ApiClient
import com.vivek.githubapisample.common.data.DataConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideBaseUrl(): String = DataConstant.BASE_URL

    @Singleton
    @Provides
    fun provideApiClient(baseUrl: String): Retrofit {
        return ApiClient.getClient(baseUrl)
    }

}
