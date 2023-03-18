package com.techafresh.skycast.domain.usecases

import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import com.techafresh.skycast.domain.repository.Repository

class GetForecastUseCase(private val repository: Repository) {
    suspend fun execute() : Forecast = repository.getForecast()
}