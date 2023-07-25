package com.techafresh.skycast.presentation.fragments.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import com.techafresh.skycast.MainActivity
import com.techafresh.skycast.R
import com.techafresh.skycast.databinding.FragmentDetailBinding
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModel

class DetailFragment : Fragment() {

    lateinit var binding : FragmentDetailBinding

    lateinit var viewModel : WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        viewModel = (activity as MainActivity).weatherViewModel

//        if (viewModel.colorX.value == "GREEN"){
////            binding.cardViewDetail.setCardBackgroundColor(resources.getColor(R.color.sd))
//        }

        try {
            viewModel.backGround.value?.let { binding.frameLayout6.setBackgroundResource(it) }
        }catch (wx : Exception){
            Log.d("MYTAG DETAILS FRAGMENT" ,"ERROR = ${wx.message}")
        }

        binding.imageViewBack1.setOnClickListener {
            it.findNavController().navigate(R.id.action_detailFragment_to_mainFragment)
        }

        viewModel.astroDetailsLiveData.observe(viewLifecycleOwner , Observer {
            binding.textViewSunriseTime.text = it.body()!!.astronomy.astro.sunset
            binding.textViewTimeSunset.text = it.body()!!.astronomy.astro.sunrise
            binding.textViewIsMoonUp.text = it.body()!!.astronomy.astro.is_moon_up.toString()
            binding.textViewMoonBrightness.text = it.body()!!.astronomy.astro.moon_illumination + "% "
//            binding.textViewMoonPhase.text = it.body()!!.astronomy.astro.moon_phase.substring()
        })

        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner , Observer {

            binding.textViewLocationX.text = it.body()!!.location.name+", "
            binding.textViewRegionX.text = it.body()!!.location.region

            Picasso.get()
                .load(formatImage(it.body()!!.current.condition.code , it.body()!!.current.is_day))
                .placeholder(R.drawable.fetcher)
                .into(binding.imageViewConditionX)

            binding.textViewTemperatureX.text = formatTemp(it.body()!!.current.temp_c)+"℃"
            binding.textViewTimeX.text = "Last Updated: " + formatTime(it.body()!!.location.localtime)

            binding.textViewHumidityX.text = it.body()!!.current.humidity.toString()+"%"
            binding.textViewAirPressureX.text = it.body()!!.current.pressure_mb.toString().substring(0,4)
            binding.textViewWindX.text = it.body()!!.current.wind_kph.toString().substring(0,1)+" km/hr"
            binding.textViewVisibilityX.text = it.body()!!.current.vis_km.toString()+" km"

            binding.textViewCloud.text = it.body()!!.current.cloud.toString()+"%"
            binding.textViewFeelsLike.text = it.body()!!.current.feelslike_c.toString()+"℃"
            binding.textViewWindDirection.text = it.body()!!.current.wind_dir


        })

        viewModel.weatherForecastLiveData.observe(viewLifecycleOwner , Observer {
            val localTime = it.body()!!.location.localtime.substring(11)
            val currentHour = localTime.substring(0,2)
            binding.textViewRain.text = it.body()!!.forecast.forecastday[0].day.daily_chance_of_rain.toString()+"%"
            binding.textViewIsDay.text = it.body()!!.forecast.forecastday[0].hour[currentHour.toInt()].is_day.toString()

        })

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