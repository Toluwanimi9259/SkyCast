package com.techafresh.skycast.presentation.hilt

import com.techafresh.skycast.domain.repository.Repository
import com.techafresh.skycast.domain.usecases.GetAstroDetailsUseCase
import com.techafresh.skycast.domain.usecases.GetCurrentWeatherUseCase
import com.techafresh.skycast.domain.usecases.GetWeatherForecastUseCase
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

    @Provides
    @Singleton
    fun provideGetWeatherForecastUseCase(repository: Repository) : GetWeatherForecastUseCase{
        return GetWeatherForecastUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAstroDetailsUseCase(repository: Repository) : GetAstroDetailsUseCase{
        return GetAstroDetailsUseCase(repository)
    }
}