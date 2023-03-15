package com.techafresh.skycast.data.room.converters

import androidx.room.TypeConverter
import com.techafresh.skycast.data.dataClasses.forecast.Condition

class Conv2 {

    @TypeConverter
    fun fromCondition(condition: Condition) : String{
        return condition.code.toString() + condition.text
    }
}