package com.techafresh.skycast.domain.usecases

import com.techafresh.skycast.data.dataClasses.forecast.Day
import com.techafresh.skycast.domain.repository.Repository
import kotlinx.coroutines.flow.Flow


class GetDayForecastUseCase(private val repository: Repository) {
    fun execute() : Flow<List<Day>> = repository.getDayForecast()
}