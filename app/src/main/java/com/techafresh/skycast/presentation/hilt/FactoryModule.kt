package com.techafresh.skycast.presentation.hilt

import android.app.Application
import com.techafresh.skycast.domain.usecases.*
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
    fun provideFactory(application: Application, getCurrentWeatherUseCase: GetCurrentWeatherUseCase, getWeatherForecastUseCase: GetWeatherForecastUseCase, getAstroDetailsUseCase: GetAstroDetailsUseCase,
                       getDayForecastUseCase: GetDayForecastUseCase,
                       saveDayForecastUseCase: SaveDayForecastUseCase,
                       deleteAllDayForecastUseCase: DeleteAllDayForecastUseCase,
                       getForecastUseCase: GetForecastUseCase,
                       saveForecastUseCase: SaveForecastUseCase
    ) : WeatherViewModelFactory{
        return WeatherViewModelFactory(application,getCurrentWeatherUseCase , getWeatherForecastUseCase , getAstroDetailsUseCase ,getDayForecastUseCase, saveDayForecastUseCase ,deleteAllDayForecastUseCase, getForecastUseCase, saveForecastUseCase)
    }
}