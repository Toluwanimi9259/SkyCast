package com.techafresh.skycast.data.retrofit.empty

import java.text.SimpleDateFormat
import java.util.Date

fun main() {
    val localTime = "2023-02-25 13:15"
    val nLocalTime = localTime.substring(0,10)
    val nLocalTimse = localTime.substring(11).substring(3)
    val localTime2 = "25/02/2023"
    val formatter = SimpleDateFormat("E, dd MMM yyy")
    val formatte2r = SimpleDateFormat("yyyy-MM-dd")
    val date1 = Date()
//    try {
        val date = formatte2r.parse(localTime)
        var strDate = formatter.format(date1)
        System.out.println(date.toString() + "||||" + strDate + " |||||" + nLocalTimse)
//    }catch (ex : Exception){
//        System.out.println("Error = ${ex.message}")
//    }
//    var strDate = formatter.format(date1)


}