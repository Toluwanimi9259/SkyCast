package com.techafresh.skycast.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.techafresh.skycast.R
import com.techafresh.skycast.data.dataClasses.forecast.Forecastday
import com.techafresh.skycast.databinding.CardItemBinding
import com.techafresh.skycast.databinding.DaysItemBinding
import java.text.SimpleDateFormat

class DaysAdapter(private val days : List<Forecastday>) : RecyclerView.Adapter<DaysAdapter.DViewHolder>(){

    inner class DViewHolder(private val binding: DaysItemBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(forecastday: Forecastday){
            var date = formatDate(forecastday.date)
            binding.textViewDate1.text = date.substring(0,4)
            binding.textViewDate22.text = date.substring(5)
            binding.textViewTemp3.text = forecastday.day.avgtemp_c.toString()+"℃"
            Picasso.get().load("https:"+forecastday.day.condition.icon).placeholder(R.drawable.mooncloudfastwind).into(binding.imageViewCondition3)
            binding.textViewCondion3.text = formatCondition(forecastday.day.condition.text)
        }

        private fun formatCondition(condition : String) : String{
            return when(condition){
                "Partly cloudy" ->  "Cloudy"
                "Sunny" -> "Sunny"
                "Cloudy" -> "Cloudy"
                "Overcast" -> "Overcast"
                "Mist" -> "Mist"
                "Blizzard" -> "Blizzard"
                "Fog" -> "Fog"
                "Heavy rain" -> "Rainy"
                "Light sleet" -> "Sleets"
                "Moderate rain" -> "Rainy"
                "Light rain" -> "Rainy"
                "Light snow" -> "Snowy"
                "Moderate snow" -> "Snowy"
                "Heavy snow" -> "Snowy"
                "Ice pellets" -> "Icy"
                "Patchy rain possible" -> "Rainy"
                "Patchy snow possible" -> "Snowy"
                "Patchy sleet possible" -> "Sleets"
                "Patchy freezing drizzle possible" -> "Drizzle"
                "Thundery outbreaks possible" -> "Thunder"
                "Blowing snow" -> "Snowy"
                "Patchy light drizzle" -> "Drizzle"
                "Patchy light rain" -> "Rainy"
                "Moderate rain at times" -> "Drizzle"
                "Heavy rain at times" -> "Rainy"
                "Heavy freezing drizzle" -> "Drizzle"
                "Freezing drizzle" -> "Drizzle"
                "Light drizzle" -> "Drizzle"
                "Freezing fog" -> "Foggy"
                "Moderate or heavy freezing rain" -> "RAINY"
                "Moderate or heavy sleet" -> "SLEETS"
                "Patchy light snow" -> "Snowy"
                "Patchy moderate snow" -> "Snowy"
                "Patchy heavy snow" -> "SNOWY"
                "Light rain shower" -> "rainy"
                "Moderate or heavy rain shower" -> "RaInY"
                "Torrential rain shower" -> "Torrents"
                "Light sleet showers" -> "Sleets"
                "Moderate or heavy sleet showers" -> "Sleets"
                "Light snow showers" -> "Sleets"
                "Moderate or heavy snow showers" -> "Showers"
                "Light showers of ice pellets" -> "Icy"
                "Moderate or heavy showers of ice pellets" -> "ICY"
                "Patchy light rain with thunder" -> "Rainy"
                "Moderate or heavy rain with thunder" -> "Rainy"
                "Patchy light snow with thunder" -> "Rainy"
                "Moderate or heavy snow with thunder" -> "Snowy"
                else -> "NNN"
            }

        }

        fun formatDate(localTime : String) : String{
//            val strDate = "2023-02-25"
            val formatter = SimpleDateFormat("E, dd MMM")
            val formatte2r = SimpleDateFormat("yyyy-MM-dd")
            val ogDate = formatte2r.parse(localTime)
            val nDate = formatter.format(ogDate)
            return if(nDate.isEmpty()){
                "NULL"
            }else{
                nDate.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DViewHolder {
        val binding = DaysItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DViewHolder(binding)
    }

    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: DViewHolder, position: Int) {
        holder.bind(days[position])
    }
}