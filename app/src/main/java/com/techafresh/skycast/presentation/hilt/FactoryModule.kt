package com.techafresh.skycast.presentation.hilt

import com.techafresh.skycast.domain.usecases.GetCurrentWeatherUseCase
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Provides
    @Singleton
    fun provideFactory(getCurrentWeatherUseCase: GetCurrentWeatherUseCase) : WeatherViewModelFactory{
        return WeatherViewModelFactory(getCurrentWeatherUseCase)
    }
}