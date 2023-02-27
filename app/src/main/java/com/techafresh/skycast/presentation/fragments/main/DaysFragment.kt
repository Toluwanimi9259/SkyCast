package com.techafresh.skycast.presentation.fragments.main

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
import com.techafresh.skycast.databinding.FragmentDaysBinding
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat


class DaysFragment : Fragment() {

    lateinit var binding: FragmentDaysBinding

    lateinit var viewModel: WeatherViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_days, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDaysBinding.bind(view)
        viewModel = (activity as MainActivity).weatherViewModel

        binding.imageViewBack.setOnClickListener {
            it.findNavController().navigate(R.id.action_daysFragment_to_mainFragment)
        }

        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner , Observer {
            try {
                binding.textViewLocation2.text = it.body()!!.location.name+", "
                binding.textViewRegion2.text = it.body()!!.location.region
                Picasso.get()
                    .load("https:"+it.body()!!.current.condition.icon)
                    .placeholder(R.drawable.suncloudmidrain)
                    .into(binding.imageViewCondition2)
                binding.textViewCondition2.text = it.body()!!.current.condition.text
                binding.textViewDate2.text = formatDate(it.body()!!.location.localtime)
                binding.textViewTemperature2.text = it.body()!!.current.temp_c.toString()+"℃"
            }catch (ex : Exception){
                Log.d("MYTAG DAYS FRAGMENT CURRENT " , "Error = ${ex.message}")
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
}