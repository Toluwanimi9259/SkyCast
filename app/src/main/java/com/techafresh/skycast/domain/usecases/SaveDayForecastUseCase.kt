package com.techafresh.skycast.domain.usecases

import com.techafresh.skycast.data.dataClasses.forecast.Day
import com.techafresh.skycast.domain.repository.Repository

class SaveDayForecastUseCase(private val repo : Repository) {
    suspend fun execute(day: Day) = repo.saveDayForecast(day)
}