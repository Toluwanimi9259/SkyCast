package com.techafresh.skycast.domain.usecases

import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import com.techafresh.skycast.domain.repository.Repository

class SaveForecastUseCase(private val repository: Repository) {
    suspend fun execute(forecast: Forecast) = repository.saveForecast(forecast)
}