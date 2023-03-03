package com.techafresh.skycast.presentation.fragments.details

import android.annotation.SuppressLint
import android.os.Bundle
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

        binding.imageViewBack1.setOnClickListener {
            it.findNavController().navigate(R.id.action_detailFragment_to_mainFragment)
        }

        viewModel.astroDetailsLiveData.observe(viewLifecycleOwner , Observer {
            binding.textViewSunriseTime.text = it.body()!!.astronomy.astro.sunrise
            binding.textViewTimeSunset.text = it.body()!!.astronomy.astro.sunset
        })

        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner , Observer {
            binding.textViewLocationX.text = it.body()!!.location.name+", "
            binding.textViewRegionX.text = it.body()!!.location.region
            Picasso.get()
                .load("https:"+it.body()!!.current.condition.icon)
                .placeholder(R.drawable.mooncloudfastwind)
                .into(binding.imageViewConditionX)
            binding.textViewTemperatureX.text = formatTemp(it.body()!!.current.temp_c)+"℃"
            binding.textViewTimeX.text = "Last Updated: " + formatTime(it.body()!!.location.localtime)

            binding.textViewHumidityX.text = it.body()!!.current.humidity.toString()+"%"
            binding.textViewAirPressureX.text = it.body()!!.current.pressure_mb.toString()
            binding.textViewWindX.text = it.body()!!.current.wind_kph.toString()+" km/hr"
            binding.textViewVisibilityX.text = it.body()!!.current.vis_km.toString()+" km"

            binding.textViewCloud.text = it.body()!!.current.cloud.toString()+"%"
            binding.textViewFeelsLike.text = it.body()!!.current.feelslike_c.toString()+"℃"
            binding.textViewWindDirection.text = it.body()!!.current.wind_dir

        })

        viewModel.weatherForecastLiveData.observe(viewLifecycleOwner , Observer {
            binding.textViewRain.text = it.body()!!.forecast.forecastday[0].day.daily_chance_of_rain.toString()+"%"

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
}