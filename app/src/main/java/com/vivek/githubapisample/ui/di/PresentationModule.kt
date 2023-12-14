package com.vivek.githubapisample.ui.di

import com.vivek.githubapisample.ui.common.ConnectivityManagerMonitor
import com.vivek.githubapisample.ui.common.NetworkMonitor
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
