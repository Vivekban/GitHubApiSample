package com.vivek.githubapisample.user.data.di

import com.vivek.githubapisample.user.data.RemoteUserRepository
import com.vivek.githubapisample.user.data.UserRemoteSource
import com.vivek.githubapisample.user.data.UserService
import com.vivek.githubapisample.user.domain.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UserServiceModule {

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return UserService.create(retrofit)
    }

}

@InstallIn(SingletonComponent::class)
@Module
abstract class UserModule {
    @Singleton
    @Binds
    abstract fun provideRepoService(userService: UserService): UserRemoteSource

    @Singleton
    @Binds
    abstract fun provideUserRepository(userRepository: RemoteUserRepository): UserRepository

}
