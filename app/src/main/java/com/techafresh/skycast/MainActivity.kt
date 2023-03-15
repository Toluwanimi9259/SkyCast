package com.techafresh.skycast
import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import com.techafresh.skycast.data.dataClasses.forecast.ForecastX
import com.techafresh.skycast.databinding.ActivityMainBinding
import com.techafresh.skycast.domain.alarms.NotificationReceiver
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModel
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val firstTimer: Boolean = false
    lateinit var binding: ActivityMainBinding

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

    // Notifications
    var notificationManager:NotificationManager? = null

    // Alarms
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()


        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        scheduleForecastNotification()
        scheduleTodayNotification()

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
                        makeSnackBarMessage("Internet Connection Error" , "INTERNET")
//                        Toast.makeText(applicationContext, "Internet Connection Problem", Toast.LENGTH_SHORT).show()
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
                makeSnackBarMessage("Location is Off" , "LOCATION")
            }
        }

        // Forecast Notification

        weatherViewModel.weatherForecastLiveData.observe(this@MainActivity, androidx.lifecycle.Observer {
                val currentHour = it.body()!!.location.localtime.substring(11).substring(0,2)
                var isDay = 0
                if (currentHour.toInt() >= 8){
                    isDay = 1
                }

            val tomorrowForecast : ForecastX = it.body()!!.forecast


                // Notfication for tommorow
                val showTime = formatTime(it.body()!!.location.localtime)
                if (showTime == "10:27 PM"){
                    pushNotification(
                        tomorrowForecast.forecastday[1].day.condition.code * 1,
                        isDay * 1,
                        it.body()!!.location.name + "",
                        it.body()!!.forecast.forecastday[1].day.condition.text + "",
                        it.body()!!.forecast.forecastday[1].day.avgtemp_c.toString() + "",
                        it.body()!!.forecast.forecastday[1].day.avgtemp_f.toString() + "",
                        "Tomorrow"
                    )
                }else if (showTime == "10:30 AM"){
                }
        })

        requestLocation()
    }

    private fun scheduleTodayNotification() {

    }

    private fun scheduleForecastNotification(){

        Log.d("MYTAG GetTime" ,"INSIDE THE Schedule Notification Method")

        val intent = Intent(applicationContext , NotificationReceiver::class.java)
        intent.putExtra("day" , "Today")
        intent.putExtra("iconCode" , 1063)
        intent.putExtra("isDay" , 1)
        intent.putExtra("userLocation" , "Ibadan")
        intent.putExtra("temp_c" , "30°")
        intent.putExtra("temp_f" , "26°")
        intent.putExtra("condition" , "Terrible Rain")
        intent.putExtra("check" , "Message Received")

        val forecastIntent = PendingIntent.getBroadcast(
            applicationContext,
            100,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val timeToRing = getTime()

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            timeToRing,
            1000 * 60 * 2,
            forecastIntent
        )

    }

    private fun getTime(): Long {
        Log.d("MYTAG GetTime" ,"INSIDE THE Get Time Method")
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY ,13)
        calendar.set(Calendar.MINUTE , 0)
        return calendar.timeInMillis
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
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                makeSnackBarMessage("Location is Off" , "LOCATION")
            }
        }
    }

    private fun makeSnackBarMessage(text : String , problem : String){
        val snackbar: Snackbar = Snackbar
            .make(binding.constMain, text, Snackbar.LENGTH_LONG)
            .setDuration(10000)
            .setAction("Turn On" , View.OnClickListener {
                if (problem == "LOCATION"){
                    requestLocation()
                }else {
                    turnOnInternet()
                }

            })
        snackbar.show()
    }

    private fun requestLocation(){
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

    private fun turnOnInternet(){
        try{
            startActivityForResult(Intent(android.provider.Settings.ACTION_NETWORK_OPERATOR_SETTINGS) , 0)
        }catch (ex : Exception){
            ex.printStackTrace()
        }
    }

    private fun formatTime(localTime: String) : String{
        val am = "AM"
        val pm = "PM"
        var cTime = "00:00 AM"
        val time = localTime.substring(11) // 13:15
        val hour = time.substring(0,2).toInt()
        val minute = time.substring(3)
        return if (hour > 12){
            val difference = hour-12
            cTime ="$difference:$minute $pm"
            "$difference:$minute $pm"
        }else{
            cTime = "$time $am"
            "$time $am"
        }
    }

    fun pushNotification(
        iconCode : Int ,
        isDay: Int ,
        userLocation : String ,
        condition : String ,
        temp_c : String ,
        temp_f : String,
        day : String
    ){

        // Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("1001", "FORECAST", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "WEATHER FORECAST"
            }

            notificationManager?.createNotificationChannel(channel)
        }

        // converting a jpeg file to Bitmap file and making an instance of Bitmap!
        val imgBitmap= BitmapFactory.decodeResource(resources,formatImage(iconCode , isDay))

        val notificationID = 45

        // Building notification
        val nBuilder= NotificationCompat.Builder(this,"1001")
            .setContentTitle("$day in $userLocation: $condition")
            .setContentText("$temp_c°/$temp_f° See full forecast")
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            // passing the Bitmap object as an argument
            .setLargeIcon(imgBitmap)
            // Expandable notification
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(imgBitmap)
                // as we pass null in bigLargeIcon() so the large icon
                // will goes away when the notification will be expanded.
                .bigLargeIcon(null))
            .build()

        notificationManager?.notify(notificationID , nBuilder)
    }

    private fun formatImage(iconCode : Int , isDay : Int) : Int{
        return when(iconCode){
            1000 -> return if (isDay == 1){
                R.drawable.daysun
            }else {
                R.drawable.nightmoon
            }

            1003 -> return if (isDay == 1){
                R.drawable.dayclouds
            }else {
                R.drawable.nightclouds
            }

            1006 -> return if (isDay == 1){
                R.drawable.dayclouds
            }else {
                R.drawable.nightclouds
            }

            1009 -> return if (isDay == 1){
                R.drawable.overcast
            }else {
                R.drawable.darkcloud
            }

            1030 -> return if (isDay == 1){
                R.drawable.group37
            }else {
                R.drawable.group37
            }

            1063 -> return if (isDay == 1){
                R.drawable.daysrain
            }else {
                R.drawable.nightrain
            }

            1066 -> return if (isDay == 1){
                R.drawable.daysnow
            }else {
                R.drawable.nightsnow
            }

            1069 -> return if (isDay == 1){
                R.drawable.group44
            }else {
                R.drawable.group43
            }

            1072 -> return if (isDay == 1){
                R.drawable.group17
            }else {
                R.drawable.group17
            }

            1087 -> return if (isDay == 1){
                R.drawable.daystorm
            }else {
                R.drawable.daystorm
            }

            1114 -> return if (isDay == 1){
                R.drawable.daystorm
            }else {
                R.drawable.daystorm
            }

            1117 -> return if (isDay == 1){
                R.drawable.group17
            }else {
                R.drawable.group17
            }

            1135 -> return if (isDay == 1){
                R.drawable.cglousd
            }else {
                R.drawable.darkcloud
            }

            1147 -> return if (isDay == 1){
                R.drawable.cglousd
            }else {
                R.drawable.darkcloud
            }

            1150 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1153 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1168 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1171 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1180 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1183 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1186 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1189 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1192 -> return if (isDay == 1){
                R.drawable.group50
            }else {
                R.drawable.group17
            }

            1195 -> return if (isDay == 1){
                R.drawable.group50
            }else {
                R.drawable.group17
            }

            1198 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1201 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1204 -> return if (isDay == 1){
                R.drawable.group47
            }else {
                R.drawable.group47
            }

            1207 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1210 -> return if (isDay == 1){
                R.drawable.group44
            }else {
                R.drawable.group43
            }

            1213 -> return if (isDay == 1){
                R.drawable.group44
            }else {
                R.drawable.group43
            }

            1216 -> return if (isDay == 1){
                R.drawable.group44
            }else {
                R.drawable.group43
            }

            1219 -> return if (isDay == 1){
                R.drawable.group44
            }else {
                R.drawable.group43
            }

            1222 -> return if (isDay == 1){
                R.drawable.group44
            }else {
                R.drawable.group43
            }

            1225 -> return if (isDay == 1){
                R.drawable.daysnow
            }else {
                R.drawable.nightsnow
            }

            1237 -> return if (isDay == 1){
                R.drawable.snow
            }else {
                R.drawable.snow
            }

            1240 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1243 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1246 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1249 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1252 -> return if (isDay == 1){
                R.drawable.group23
            }else {
                R.drawable.group23
            }

            1255 -> return if (isDay == 1){
                R.drawable.group43
            }else {
                R.drawable.group43
            }

            1258 -> return if (isDay == 1){
                R.drawable.group43
            }else {
                R.drawable.group43
            }

            1261 -> return if (isDay == 1){
                R.drawable.group43
            }else {
                R.drawable.group43
            }

            1264 -> return if (isDay == 1){
                R.drawable.group43
            }else {
                R.drawable.group43
            }

            1273 -> return if (isDay == 1){
                R.drawable.group42
            }else {
                R.drawable.group42
            }

            1276 -> return if (isDay == 1){
                R.drawable.daystorm
            }else {
                R.drawable.nightstorm
            }

            1279 -> return if (isDay == 1){
                R.drawable.daysnow
            }else {
                R.drawable.nightsnow
            }

            1282 -> return if (isDay == 1){
                R.drawable.daystorm
            }else {
                R.drawable.nightstorm
            }

            else -> R.drawable.group16
        }



    }


}
