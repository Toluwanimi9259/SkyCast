package com.techafresh.skycast.domain.usecases

import com.techafresh.skycast.domain.repository.Repository

class DeleteAllDayForecastUseCase(private val repository: Repository) {
    suspend fun execute() = repository.deleteAllDayForecast()
}