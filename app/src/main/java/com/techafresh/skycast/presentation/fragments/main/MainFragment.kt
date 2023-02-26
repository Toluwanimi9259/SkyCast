package com.techafresh.skycast.presentation.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.techafresh.skycast.MainActivity
import com.techafresh.skycast.R
import com.techafresh.skycast.databinding.FragmentMainBinding
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat


class MainFragment : Fragment() {

    lateinit var binding : FragmentMainBinding

    lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        viewModel = (activity as MainActivity).weatherViewModel

        binding.textView7Days.setOnClickListener{
            it.findNavController().navigate(R.id.action_mainFragment_to_daysFragment)
        }

        viewModel.astroDetailsLiveData.observe(viewLifecycleOwner , Observer {
            try {
                Log.d("MYTAG ASTRO MAIN FRAGMENT", it.body().toString())
            } catch (ex: Exception) {
                Log.d("MAINFRAGMENT ASTRO", "Error = " + ex.message)
            }
        })

        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner, Observer {
            try {
                Log.d("MYTAG CURRENT", it.body().toString())
                binding.textViewCondition.text = it.body()!!.current.condition.text
                binding.textViewLocation.text = it.body()!!.location.name
                binding.textViewRegion.text = it.body()!!.location.region
//                Glide
//                    .with(binding.textViewLocation.context)
//                    .load(it.body()!!.current.condition.icon)
//                    .into(binding.imageViewCondition)
                binding.textViewDate.text = formatDate(it.body()!!.location.localtime)
                binding.textViewTemperature.text = it.body()!!.current.temp_c.toString()+"℃"
                binding.textViewTime.text = formatTime(it.body()!!.location.localtime)
                binding.textViewHumidity.text = it.body()!!.current.humidity.toString()+"%"
                binding.textViewAirPressure.text = it.body()!!.current.pressure_mb.toString()
                binding.textViewWind.text = it.body()!!.current.wind_kph.toString()+" km/hr"
                binding.textViewVisibility.text = it.body()!!.current.vis_km.toString()+" km"

            } catch (ex: Exception) {
                Log.d("MAINACTIVITY CURRENT", "Error = " + ex.message)
            }
        })
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
}