package com.techafresh.skycast.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techafresh.skycast.domain.usecases.*

class WeatherViewModelFactory(
    private val app : Application,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    private val getAstroDetailsUseCase: GetAstroDetailsUseCase,
    private val getDayForecastUseCase: GetDayForecastUseCase,
    private val saveDayForecastUseCase: SaveDayForecastUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(app , getCurrentWeatherUseCase , getWeatherForecastUseCase ,getAstroDetailsUseCase , getDayForecastUseCase, saveDayForecastUseCase) as T
    }
}