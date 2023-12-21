package com.vivek.githubapisample.repo.data.di

import com.vivek.githubapisample.repo.data.RemoteRepoRepository
import com.vivek.githubapisample.repo.data.RepoRemoteSource
import com.vivek.githubapisample.repo.data.RepoService
import com.vivek.githubapisample.repo.domain.RepoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * A module that provides the [RepoService] dependency.
 */
@InstallIn(SingletonComponent::class)
@Module
class RepoServiceModule {

    /**
     * Provides the [RepoService] dependency.
     */
    @Singleton
    @Provides
    fun provideRepoService(retrofit: Retrofit): RepoService {
        return RepoService.create(retrofit)
    }

}

/**
 * A module that provides dependencies for the data layer.
 *
 * This module is installed into the [SingletonComponent].
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class RepoModule {

    /**
     * Provides the [RepoService] implementation.
     */
    @Singleton
    @Binds
    abstract fun provideRepoService(repoService: RepoService): RepoRemoteSource

    /**
     * Provides the [RepoRepository] implementation.
     */
    @Singleton
    @Binds
    abstract fun provideRepoRepository(repoRepository: RemoteRepoRepository): RepoRepository

}
