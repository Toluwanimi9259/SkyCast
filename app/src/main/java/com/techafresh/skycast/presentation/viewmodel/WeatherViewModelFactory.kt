package com.techafresh.skycast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techafresh.skycast.domain.usecases.GetCurrentWeatherUseCase
import com.techafresh.skycast.domain.usecases.GetWeatherForecastUseCase

class WeatherViewModelFactory(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(getCurrentWeatherUseCase , getWeatherForecastUseCase) as T
    }
}