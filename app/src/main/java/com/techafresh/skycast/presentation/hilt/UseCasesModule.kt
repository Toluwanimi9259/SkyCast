package com.techafresh.skycast.presentation.hilt

import com.techafresh.skycast.domain.repository.Repository
import com.techafresh.skycast.domain.usecases.GetCurrentWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    @Singleton
    fun provideGetCurrentWeatherUseCase(repository: Repository) : GetCurrentWeatherUseCase{
        return GetCurrentWeatherUseCase(repository)
    }
}