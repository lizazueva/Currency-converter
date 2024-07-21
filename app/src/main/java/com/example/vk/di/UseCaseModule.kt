package com.example.vk.di

import com.example.domain.repository.TopRepository
import com.example.domain.usecase.GetRatesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetArtWorksUseCase(repository: TopRepository): GetRatesUseCase {
        return GetRatesUseCase(repository)
    }
}