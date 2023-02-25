package com.techafresh.skycast.data.dataClasses.forecast

data class Forecast(
    val alerts: Alerts,
    val current: Current,
    val forecast: ForecastX,
    val location: Location
)