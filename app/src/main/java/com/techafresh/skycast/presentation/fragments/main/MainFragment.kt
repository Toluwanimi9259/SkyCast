package com.techafresh.skycast.presentation.fragments.main

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.squareup.picasso.Picasso
import com.techafresh.skycast.MainActivity
import com.techafresh.skycast.R
import com.techafresh.skycast.databinding.FragmentMainBinding
import com.techafresh.skycast.presentation.adapters.HoursAdapter
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat


class MainFragment : Fragment() {

    lateinit var binding : FragmentMainBinding

    lateinit var viewModel: WeatherViewModel

    lateinit var hoursAdapter: HoursAdapter

    var notificationManager:NotificationManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        viewModel = (activity as MainActivity).weatherViewModel
//        binding.textViewCondition.text = viewModel.colorX.value

        val backGround = viewModel.backGround
        val color = viewModel.colorX
        Log.d("MTAG MAIN FRAGMENT" , "Background = ${backGround.value} Color = ${color.value}")

//        if (color.value == "GREEN"){
////            binding.cardViewMain.setCardBackgroundColor(resources.getColor(R.color.sd))
//            binding.knowMoreBtn.setBackgroundResource(R.drawable.roujn)
////            binding.rV.setBackgroundColor(resources.getColor(R.color.sd))
//        }else {
//            binding.knowMoreBtn.setBackgroundColor(R.drawable.know_morebtn)
//        }

        try {
            backGround.value?.let { binding.frameLayout4.setBackgroundResource(it) }
        }catch (wx : Exception){
            Log.d("MYTAG MAIN FRAGMENT" ,"ERROR = ${wx.message}")
        }

        binding.textView7Days.setOnClickListener{
            it.findNavController().navigate(R.id.action_mainFragment_to_daysFragment)
        }
        binding.knowMoreBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
        }

        viewModel.astroDetailsLiveData.observe(viewLifecycleOwner , Observer {
            try {
//                Log.d("MYTAG ASTRO MAIN FRAGMENT", it.body().toString())
            } catch (ex: Exception) {
                Log.d("MAINFRAGMENT ASTRO", "Error = " + ex.message)
            }
        })

        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner, Observer {
            try {
                Log.d("MYTAG CURRENT", it.body().toString())
                binding.textViewCondition.text = it.body()!!.current.condition.text
                binding.textViewLocation.text = it.body()!!.location.name+", "
                binding.textViewRegion.text = it.body()!!.location.region
//                Glide
//                    .with(binding.textViewLocation.context)
//                    .load(it.body()!!.current.condition.icon)
//                    .into(binding.imageViewCondition)
                binding.textViewDate.text = formatDate(it.body()!!.location.localtime)
                binding.textViewTemperature.text = formatTemp(it.body()!!.current.temp_c)+"℃"
                binding.textViewTime.text = "Last Updated: " + formatTime(it.body()!!.location.localtime)
                binding.textViewHumidity.text = it.body()!!.current.humidity.toString()+"%"
                binding.textViewAirPressure.text = it.body()!!.current.pressure_mb.toString()
                binding.textViewWind.text = it.body()!!.current.wind_kph.toString()+" km/hr"
                binding.textViewVisibility.text = it.body()!!.current.vis_km.toString()+" km"
                Picasso.get()
                    .load(formatImage(it.body()!!.current.condition.code , it.body()!!.current.is_day))
                    .placeholder(R.drawable.fetcher)
                    .into(binding.imageViewCondition)

            } catch (ex: Exception) {
                Log.d("MAINACTIVITY CURRENT", "Error = " + ex.message)
            }
        })

        viewModel.weatherForecastLiveData.observe(viewLifecycleOwner , Observer {
            // Getting current hour
            val localTime = it.body()!!.location.localtime.substring(11)
            val currentHour = localTime.substring(0,2)

            // Save Day Forecast to db
//            viewModel.saveDayForecast(it.body()!!.forecast.forecastday[0].day)

            hoursAdapter = HoursAdapter(it.body()!!.forecast.forecastday[0].hour , currentHour ,
                viewModel.colorX.value.toString()
            )
            initRecyclerview(hoursAdapter)
            try {
//                val hour = it.body().forecast.forecastday[0].hour[0].cloud
//                Log.d("MYTAG FORECAST", it.body().toString())
            } catch (ex: Exception) {
                Log.d("MAINACTIVITY FORECAST", "Error = " + ex.message)
            }
        })
    }

    private fun initRecyclerview(hoursAdapter: HoursAdapter){
        binding.rV.apply {
            adapter = hoursAdapter
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        }
    }

    private fun formatDate(localTime : String) : String{
        val newLocalTime = localTime.substring(0,10)
        val formatter2 = SimpleDateFormat("E, dd MMM yyy")
        val formatter1 = SimpleDateFormat("yyyy-MM-dd")

        try {
            val originalDate = formatter1.parse(newLocalTime)
            val formattedDate = formatter2.format(originalDate!!)
            return formattedDate.toString()
        }catch (ex : Exception){
            Log.d("MYTAG TIME CONVERTER" , "Error = ${ex.message}")
        }
        return newLocalTime
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

    private fun formatTemp(temp : Double) : String{
        val temperature = temp.toString()
        val index = temperature.lastIndexOf('.')
        val prefix = temperature.substring(0,index)
        val suffix = temperature.substring(index+1)
        val prefixI = prefix.toInt()
        val suffixI = suffix.toInt()

        return if (suffixI >= 5){
            "${prefixI+1}"
        }else{
            prefix
        }

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

    fun pushNotification(
        iconCode : Int ,
        isDay: Int ,
        userLocation : String ,
        condition : String ,
        temp_c : String ,
        temp_f : String
    ){

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
        val nBuilder= NotificationCompat.Builder(requireActivity().applicationContext,"1001")
            .setContentTitle("Tomorrow in $userLocation: $condition")
            .setContentText("$temp_c°/$temp_f See full forecast")
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
            // passing the Bitmap object as an argument
            .setLargeIcon(imgBitmap)
            // Expandable notification
            .setStyle(
                NotificationCompat.BigPictureStyle()
                .bigPicture(imgBitmap)
                // as we pass null in bigLargeIcon() so the large icon
                // will goes away when the notification will be expanded.
                .bigLargeIcon(null))
            .build()

        notificationManager?.notify(notificationID , nBuilder)
    }
}