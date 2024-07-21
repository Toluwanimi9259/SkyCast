package com.techafresh.skycast

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.techafresh.skycast.notifications.GetDataWorker
import com.techafresh.skycast.notifications.SendNotifWorker
import com.techafresh.skycast.notifications.SendNotifWorkerAlarm
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class WeatherApp : Application(){

    private val locationScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    @Inject lateinit var workerFactory: HiltWorkerFactory


    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        startLocationUpdates()
    }


    private fun setupWorker() {
        val getDataRequest = PeriodicWorkRequestBuilder<GetDataWorker>(16, TimeUnit.MINUTES)
            .setInitialDelay(20, TimeUnit.SECONDS)
            .build()

//        val periodicWorkRequest = PeriodicWorkRequestBuilder<SendNotifWorker>(16, TimeUnit.MINUTES)
//            .setInitialDelay(40, TimeUnit.SECONDS)
//            .build()

        val alarmWorker = PeriodicWorkRequestBuilder<SendNotifWorkerAlarm>(16, TimeUnit.MINUTES)
            .setInitialDelay(40, TimeUnit.SECONDS)
            .build()

        val workManager = WorkManager.getInstance(applicationContext)

        workManager.enqueueUniquePeriodicWork("periodic_getting_data_work", ExistingPeriodicWorkPolicy.KEEP, getDataRequest)

//        workManager.enqueueUniquePeriodicWork("periodic_notification_work", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest)

        workManager.enqueueUniquePeriodicWork("periodic_alarm_work", ExistingPeriodicWorkPolicy.KEEP, alarmWorker)
    }


    fun startLocationUpdates() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    handleLocationUpdate(location)
                }
            }
        }

        val locationRequest = LocationRequest.create().apply {
            interval = 10000 // Update interval (adjust as needed)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
            setupWorker()
        }else{

        }
    }

    private fun handleLocationUpdate(location: Location?) {
        locationScope.launch {
            val latitude = location?.latitude ?: 48.85 // Location for Eichstätt Germany
            val longitude = location?.longitude ?: 11.20

            // Store location data in SharedPreferences
            val sharedPreferences = getSharedPreferences("user_location", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()){
                putString("latitude", latitude.toString())
                putString("longitude", longitude.toString())
                apply()
            }

        }
    }

    override fun onTerminate() {
        super.onTerminate()
        // Stop location updates when the application terminates
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}