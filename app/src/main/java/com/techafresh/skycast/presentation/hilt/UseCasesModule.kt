package com.techafresh.skycast.presentation.hilt

import com.techafresh.skycast.domain.repository.Repository
import com.techafresh.skycast.domain.usecases.*
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

    @Provides
    @Singleton
    fun provideSaveDayForecastUseCase(repository: Repository) : SaveDayForecastUseCase{
        return SaveDayForecastUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetDayForecastUseCase(repository: Repository) : GetDayForecastUseCase{
        return GetDayForecastUseCase(repository)
    }
}