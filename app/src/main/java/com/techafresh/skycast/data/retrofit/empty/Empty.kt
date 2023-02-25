package com.techafresh.skycast.data.retrofit.empty
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.location.Location
//import android.location.LocationListener
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//
//class Empty : AppCompatActivity() {
//    var mLocationListener: LocationListener? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mLocationListener = object : LocationListener {
//            override fun onLocationChanged(location: Location) {}
//            override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
//            override fun onProviderEnabled(s: String) {}
//            override fun onProviderDisabled(s: String) {
//                Log.d("Clima", "onProviderDisabled() callback recieved")
//            }
//        }
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) !== PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            ActivityCompat.requestPermissions(
//                this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE
//            )
//            return
//        }
//    }
//}