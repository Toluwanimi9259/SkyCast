package com.techafresh.skycast.data.retrofit.empty

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

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

fun main(args: Array<String>) {
    val strDate = "2023-02-25"
    val formatter = SimpleDateFormat("E, dd MMM")
    val formatte2r = SimpleDateFormat("yyyy-MM-dd")
    val ogDate = formatte2r.parse(strDate)
    val nDate = formatter.format(ogDate)
//    System.out.println(nDate.substring(0,4))
//    System.out.println(nDate.substring(5))

    val temp = "100.5"
    val tt = temp.replace('.' , ' ')
    val index = temp.lastIndexOf('.')
    val tt2 = temp.substring(0,index)
    var prefix = tt2.toInt()
    val sufix = temp.substring(index+1)
    val suffix = sufix.toInt()
    var tempX = tt2
    if (suffix >= 5){
        prefix += 1
        tempX = prefix.toString()
    }
//    System.out.println("|||"+tempX+"|||")



    val calendar = Calendar.getInstance()
    val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")

    val currentDate = simpleDateFormat.format(calendar.time)
//    System.out.println("SDF = ${SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().time)}")

    System.out.println("DateFormat = " + DateFormat.getDateTimeInstance().format(Date()))
//    SimpleDateFormat("E, dd MMM").format(Calendar.getInstance().time)
}