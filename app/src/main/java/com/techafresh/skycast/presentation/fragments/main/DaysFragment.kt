package com.techafresh.skycast.presentation.fragments.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.techafresh.skycast.MainActivity
import com.techafresh.skycast.R
import com.techafresh.skycast.databinding.FragmentDaysBinding
import com.techafresh.skycast.presentation.adapters.DaysAdapter
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat


class DaysFragment : Fragment() {

    lateinit var binding: FragmentDaysBinding

    lateinit var viewModel: WeatherViewModel

    lateinit var daysAdapter: DaysAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_days, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDaysBinding.bind(view)
        viewModel = (activity as MainActivity).weatherViewModel

//        if (viewModel.colorX.value == "GREEN"){
////            binding.cardViewDays.setCardBackgroundColor(resources.getColor(R.color.sd))
//        }

        try {
            viewModel.backGround.value?.let { binding.frameLayout5.setBackgroundResource(it) }
        }catch (wx : Exception){
            Log.d("MYTAG DAYS FRAGMENT" ,"ERROR = ${wx.message}")
        }

        binding.imageViewBack.setOnClickListener {
            it.findNavController().navigate(R.id.action_daysFragment_to_mainFragment)
        }

        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner , Observer {
            try {
                binding.textViewLocation2.text = it.body()!!.location.name+", "
                binding.textViewRegion2.text = it.body()!!.location.region
                Picasso.get()
                    .load(formatImage(it.body()!!.current.condition.code , it.body()!!.current.is_day))
                    .placeholder(R.drawable.fetcher)
                    .into(binding.imageViewCondition2)
                binding.textViewCondition2.text = it.body()!!.current.condition.text
                binding.textViewDate2.text = formatDate(it.body()!!.location.localtime)
                binding.textViewTemperature2.text = formatTemp(it.body()!!.current.temp_c)+"℃"
            }catch (ex : Exception){
                Log.d("MYTAG DAYS FRAGMENT CURRENT " , "Error = ${ex.message}")
            }
        })

        viewModel.weatherForecastLiveData.observe(viewLifecycleOwner , Observer {
            try {
                initRV(DaysAdapter(it.body()!!.forecast.forecastday))
            }catch (ex : Exception){
                Log.d("MYTAG DAYS FRAGMENT FORECAST" , "Error = ${ex.message}")
            }
        })

    }

    private fun initRV(daysAdapter: DaysAdapter){
        binding.rV2.apply {
            adapter = daysAdapter
            layoutManager = LinearLayoutManager(activity)
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
}