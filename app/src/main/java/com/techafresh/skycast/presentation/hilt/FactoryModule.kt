package com.techafresh.skycast.presentation.hilt

import android.app.Application
import com.techafresh.skycast.domain.usecases.GetAstroDetailsUseCase
import com.techafresh.skycast.domain.usecases.GetCurrentWeatherUseCase
import com.techafresh.skycast.domain.usecases.GetWeatherForecastUseCase
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
    fun provideFactory(application: Application,getCurrentWeatherUseCase: GetCurrentWeatherUseCase , getWeatherForecastUseCase: GetWeatherForecastUseCase , getAstroDetailsUseCase: GetAstroDetailsUseCase) : WeatherViewModelFactory{
        return WeatherViewModelFactory(application,getCurrentWeatherUseCase , getWeatherForecastUseCase , getAstroDetailsUseCase)
    }
}