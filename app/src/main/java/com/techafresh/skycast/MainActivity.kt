package com.techafresh.skycast

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModel
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val firstTimer : Boolean = false

    lateinit var weatherViewModel: WeatherViewModel

    @Inject
    lateinit var weatherViewModelFactory : WeatherViewModelFactory

    lateinit var sharedPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences("First_Timer_Checker" , Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isFirstTimer" , firstTimer)
        editor.apply()

        // Initializing the ViewModel
        weatherViewModel = ViewModelProvider(this , weatherViewModelFactory)[WeatherViewModel::class.java]

        // Current Weather
        weatherViewModel.getCurrentWeatherData("london")
        weatherViewModel.currentWeatherLiveData.observe(this , Observer {
            try {
                Log.d("MYTAG" , it.body().toString())
            }catch (ex : Exception){
                Log.d("MAINACTIVITY" , "Error = " + ex.message)
            }

        })


    }
}