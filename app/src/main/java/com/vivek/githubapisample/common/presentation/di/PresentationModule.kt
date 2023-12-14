package com.vivek.githubapisample.common.presentation.di

import com.vivek.githubapisample.common.presentation.ConnectivityManagerMonitor
import com.vivek.githubapisample.common.presentation.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UiModule {
    @Binds
    abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerMonitor
    ): NetworkMonitor
}
