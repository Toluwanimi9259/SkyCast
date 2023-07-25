package com.techafresh.skycast.presentation.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.techafresh.skycast.R
import com.techafresh.skycast.data.dataClasses.forecast.Forecastday
import com.techafresh.skycast.data.dataClasses.forecast.Hour
import com.techafresh.skycast.databinding.CardItemBinding

class HoursAdapter(private val hours : List<Hour> , private val currentHour : String , private val bgColor : String) : RecyclerView.Adapter<HoursAdapter.DaysViewHolder>() {

    inner class DaysViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("ResourceAsColor")
        fun bind(hour : Hour){

            if (bgColor == "GREEN"){
//                binding.cardViewHour.setCardBackgroundColor(R.color.sd)
            }
            binding.textViewHour.text = formatTime(hour.time)
            Picasso.get().load("https:"+hour.condition.icon).into(binding.imageViewCondition4)
            Log.d("MYTAG IMAGE URL = " , hour.condition.icon)
//            Glide.with(binding.textViewTemp.context).load("http://goo.gl/gEgYUd").placeholder(R.drawable.cloudzap).into(binding.imageViewCondition4)
            binding.textViewTemp.text = hour.temp_c.toString()+"℃"
        }
        private fun formatTime(localTime: String) : String{
            val time = localTime.substring(11) // 13:00
            val hour = time.substring(0,2) // 13
            return if(hour == currentHour){
                "Now"
            }else{
                time
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DaysViewHolder(binding)
    }

    override fun getItemCount(): Int = hours.size

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        holder.bind(hours[position])
    }
}