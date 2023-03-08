package com.techafresh.skycast
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.*
import android.media.tv.AdRequest
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModel
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val firstTimer: Boolean = false

    lateinit var weatherViewModel: WeatherViewModel

    @Inject
    lateinit var weatherViewModelFactory: WeatherViewModelFactory

    lateinit var sharedPreferences: SharedPreferences

    // Location
    private val requestcode = 22
//    lateinit var geocoder: Geocoder
    lateinit var listAddress : List<Address>
    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener
    lateinit var userLocation : Location

    // Date
    lateinit var calendar: Calendar
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var currentDate : String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()

        sharedPreferences = this.getSharedPreferences("First_Timer_Checker", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isFirstTimer", firstTimer)
        editor.apply()

        val background : Int = intent.getIntExtra("background" , R.drawable.daystorm)
        val color : String = intent.getStringExtra("color").toString()
//        Log.d("MYTAG SHIIIT " , "BACKGROUND =$background , Color = $color")

        // Initializing the ViewModel
        weatherViewModel = ViewModelProvider(this, weatherViewModelFactory)[WeatherViewModel::class.java]
        weatherViewModel.backGround.value = background
        weatherViewModel.colorX.value = color

        // Date....
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
        currentDate = simpleDateFormat.format(calendar.time)
        Log.d("MYTAG DATE", " Current Date = ${currentDate.toString()}")

        // Location TINZ
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {

                val geocoder = Geocoder(applicationContext, Locale.getDefault())

                try {
                    if (isNetworkAvailable(applicationContext)){
                        listAddress = geocoder.getFromLocation(location.latitude, location.longitude, 1)!!

                        // Current Weather
                        weatherViewModel.getCurrentWeatherData(listAddress[0].locality)

                        // Forecast Weather
                        weatherViewModel.getWeatherForecast(listAddress[0].locality)

                        // Astro
                        weatherViewModel.getAstroDetails(currentDate, listAddress[0].locality)

                    }else{
                        Toast.makeText(applicationContext, "Internet Connection Problem", Toast.LENGTH_SHORT).show()
                        Log.d("MYTAG", "Internet Connection Problem")
                    }
                }catch (ex : Exception){
                    Log.d("MYTAG GEOCODER" ," Exception = ${ex.message}")
                }
                userLocation = location
//                Toast.makeText(this@MainActivity, "You are in ${listAddress[0].locality}", Toast.LENGTH_SHORT).show()
//                Log.d("MYTAG" , "City = ${listAddress[0].locality}")
            }
            override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
            override fun onProviderEnabled(s: String) {}
            override fun onProviderDisabled(s: String) {
                Toast.makeText(this@MainActivity, "Please Turn on your Location", Toast.LENGTH_LONG).show()
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                requestcode
            )
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION), requestcode
            )
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 30000 , 0F,locationListener)
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

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == requestcode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
