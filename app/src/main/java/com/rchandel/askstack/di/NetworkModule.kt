package com.rchandel.askstack.di

import android.content.Context
import com.rchandel.askstack.core.network.ConnectivityObserver
import com.rchandel.askstack.core.network.NetworkConnectivityObserver
import com.rchandel.askstack.data.remote.api.ApiClient
import com.rchandel.askstack.data.remote.api.StackOverflowApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideApiService(): StackOverflowApi {
        return ApiClient.create()
    }

    @Provides
    fun provideConnectivityObserver(
        @ApplicationContext context: Context
    ): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }
}