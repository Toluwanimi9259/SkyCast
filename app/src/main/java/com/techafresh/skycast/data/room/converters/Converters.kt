package com.techafresh.skycast.data.room.converters

import androidx.room.TypeConverter
import com.techafresh.skycast.data.dataClasses.forecast.Condition

class ConvertersX {

    @TypeConverter
    fun intToCondition(code: Int) : Condition{
        return Condition(code, code.toString(), code.toString())
    }

    @TypeConverter
    fun conditionToInt(cond : Condition) : Int{
        return cond.code
    }

}