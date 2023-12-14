package com.vivek.githubapisample.repo.di

import com.vivek.githubapisample.repo.data.RemoteRepoRepository
import com.vivek.githubapisample.repo.data.RepoService
import com.vivek.githubapisample.repo.domain.RepoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepoServiceModule {

    @Singleton
    @Provides
    fun provideRepoService(retrofit: Retrofit): RepoService {
        return RepoService.create(retrofit)
    }

}

@InstallIn(SingletonComponent::class)
@Module
abstract class RepoModule {

    @Singleton
    @Binds
    abstract fun provideRepoRepository(repoRepository: RemoteRepoRepository): RepoRepository

}
