package com.techafresh.skycast.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.techafresh.skycast.BuildConfig
import com.techafresh.skycast.data.retrofit.api.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetDataWorker(
    private val appContext : Context,
    private val parameters: WorkerParameters
) : CoroutineWorker(appContext , parameters){

    private fun processChance(percentChanceOfRain : Int, avgHumidity :Int): String{
        // Will be improved later
        return if (percentChanceOfRain >= 60){
            if (avgHumidity >= 70){
                "You should definitely take your umbrella today!!"
            }else{
                "You should take your umbrella today!"
            }
        }else if (percentChanceOfRain in 40..59){
            "You might take your umbrella today just to be safe!"
        }else{
            "Doesn't that feel good"
        }
    }

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO){
            val weatherAPI = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java) ?: return@withContext Result.failure()


            val location = appContext.getSharedPreferences("user_location", Context.MODE_PRIVATE) ?: return@withContext Result.failure()
            val latitude = location.getString("latitude", "48.85")?.toDouble() // Sivry france
            val longitude = location.getString("longitude", "6.20")?.toDouble()


            val sharedPref = appContext.getSharedPreferences("getdataworker", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()

            val weatherForecast = weatherAPI.getWeatherForecast("$latitude,$longitude").body()!!

            val percentChanceOfRain = weatherForecast.forecast.forecastday[0].day.daily_chance_of_rain
            val avgHumidity = weatherForecast.forecast.forecastday[0].day.avghumidity

            editor.putString("title", "There is a $percentChanceOfRain% chance that it will rain today.")
            editor.putString("icon", weatherForecast.forecast.forecastday[0].day.condition.code.toString() + "#" + weatherForecast.current.is_day.toString())
            editor.putString("debug", "Location = ${weatherForecast.location.name} || Local Time = ${weatherForecast.location.localtime}") // Current Time
            editor.putString("sub_text", processChance(percentChanceOfRain, avgHumidity.toInt()))
            editor.apply()
            Result.success()
        }
    }
}