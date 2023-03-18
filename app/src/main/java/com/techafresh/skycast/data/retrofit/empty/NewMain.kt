import com.google.gson.Gson
import com.techafresh.skycast.data.retrofit.empty.Alert
import com.techafresh.skycast.data.retrofit.empty.Loca
import com.techafresh.skycast.data.retrofit.empty.ObjectX
import com.techafresh.skycast.data.retrofit.empty.Tensa

fun main(args: Array<String>) {
    val obj : ObjectX = ObjectX(
        1 ,
        Alert(11 , "There is a Fire") ,
        Loca(10.3 , 10.4) ,
        Tensa(1244555599 , 2023)
    )

//    System.out.println("")

    System.out.println("Normal Object = $obj")

    val gson = Gson()

}