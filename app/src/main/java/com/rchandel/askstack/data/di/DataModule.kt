package com.rchandel.askstack.data.di

import com.rchandel.askstack.data.remote.api.StackOverflowApi
import com.rchandel.askstack.data.repository.StackOverflowRepositoryImpl
import com.rchandel.askstack.domain.repository.StackOverflowRepository
import com.rchandel.askstack.domain.usecase.SearchQuestionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideStackOverflowRepository(apiService: StackOverflowApi): StackOverflowRepository {
        return StackOverflowRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideSearchQuestionsUseCase(repository: StackOverflowRepository): SearchQuestionsUseCase {
        return SearchQuestionsUseCase(repository)
    }
}