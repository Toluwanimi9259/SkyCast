package com.techafresh.skycast.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.techafresh.skycast.data.dataClasses.current.Current
import com.techafresh.skycast.data.dataClasses.astronomy.Astro
import com.techafresh.skycast.data.dataClasses.forecast.Day
import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import com.techafresh.skycast.domain.repository.Repository
import com.techafresh.skycast.domain.usecases.*
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel(
    private val app : Application,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    private val getAstroDetailsUseCase: GetAstroDetailsUseCase,
    private val getDayForecastUseCase: GetDayForecastUseCase,
    private val saveDayForecastUseCase: SaveDayForecastUseCase
) : ViewModel() {

    var backGround : MutableLiveData<Int> = MutableLiveData()
    var colorX : MutableLiveData<String> = MutableLiveData()

    val currentWeatherLiveData : MutableLiveData<Response<Current>> = MutableLiveData()
    fun getCurrentWeatherData(location : String) = viewModelScope.launch {
        try {
            if (isNetworkAvailable(app)){
                currentWeatherLiveData.postValue(getCurrentWeatherUseCase.execute(location))
            }else{
                Toast.makeText(app, "Network Problem CURRENT", Toast.LENGTH_SHORT).show()
            }
            
        }catch (ex : Exception){
            Log.d("WEATHER VIEW MODEL CURRENT" , "Error = " + ex.message)
        }
    }

    val weatherForecastLiveData : MutableLiveData<Response<Forecast>> = MutableLiveData()

    fun getWeatherForecast(location: String) = viewModelScope.launch {
        try {
            if (isNetworkAvailable(app)){
                weatherForecastLiveData.postValue(getWeatherForecastUseCase.execute(location))
            }else{
                Toast.makeText(app, "Network Problem FORECAST", Toast.LENGTH_SHORT).show()
            }

        }catch (ex : Exception){
            Log.d("WEATHER VIEW MODEL FORECAST" , "Error = " + ex.message)
        }
    }

    val astroDetailsLiveData : MutableLiveData<Response<Astro>> = MutableLiveData()

    fun getAstroDetails(date : String , location: String) = viewModelScope.launch {
        try {
            if (isNetworkAvailable(app)){
                astroDetailsLiveData.postValue(getAstroDetailsUseCase.execute(date, location))
            }else{
                Toast.makeText(app, "Network Problem ASTRO", Toast.LENGTH_SHORT).show()
            }
        }catch (ex : Exception){
            Log.d("WEATHER VIEW MODEL ASTRO" , "Error = " + ex.message)
        }
    }

//    val dayForecastLiveData : MutableLiveData<List<Day>> = MutableLiveData()

    fun getDayForecast() = liveData {
        getDayForecastUseCase.execute().collect(){
            emit(it)
        }
    }

    fun saveDayForecast(day: Day) = viewModelScope.launch {
        saveDayForecastUseCase.execute(day)
    }

    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }
}