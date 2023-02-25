package com.techafresh.skycast.domain.usecases

import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import com.techafresh.skycast.domain.repository.Repository
import retrofit2.Response

class GetWeatherForecastUseCase(private val repository: Repository) {
    suspend fun execute(location : String) : Response<Forecast> = repository.getWeatherForecast(location)
}