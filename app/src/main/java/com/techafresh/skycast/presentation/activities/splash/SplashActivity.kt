package com.techafresh.skycast.presentation.activities.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.techafresh.skycast.MainActivity
import com.techafresh.skycast.R
import com.techafresh.skycast.databinding.ActivitySplashBinding
import com.techafresh.skycast.presentation.activities.onboarding.OnboardingActivity
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModel
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences

    lateinit var binding: ActivitySplashBinding

    var generatedBackground = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        var back = "back  21 22 24 27 29 30 31 32 "
//        var need_green = "20 23 ,25 35"


        generatedBackground = generateRandomBackground()


        Log.d("MYTAG SPLASH ACTIVITY" , "Generated = $generatedBackground , Color = ${changeUI(generatedBackground)}")

        generatedBackground.let { binding.consT.setBackgroundResource(it) }
        
        sharedPreferences = this.getSharedPreferences("First_Timer_Checker" , Context.MODE_PRIVATE)

        Handler().postDelayed(Runnable {
            checkFirstTimer()
            finish()
        }, 3000)

    }

    private fun checkFirstTimer(){
        if (!sharedPreferences.getBoolean("isFirstTimer" , true)){
            startActivity(Intent(this@SplashActivity , MainActivity::class.java)
                .putExtra("color" , changeUI(generatedBackground))
                .putExtra("background" , generatedBackground)
            )
        }else{
            startActivity(Intent(this@SplashActivity , OnboardingActivity::class.java))
        }
    }

    private fun generateRandomBackground() : Int{
        return when(java.util.Random().nextInt(15)){
            0 -> R.drawable.back20
            1 -> R.drawable.back21
            2 -> R.drawable.back22
            3 -> R.drawable.back23
            4 -> R.drawable.back24
            5 -> R.drawable.back25
            6 -> R.drawable.back26
            7 -> R.drawable.back27
            8 -> R.drawable.back28
            9 -> R.drawable.back29
            10 -> R.drawable.back30
            11 -> R.drawable.back31
            12 -> R.drawable.back32
            13 -> R.drawable.back35
            14 -> R.drawable.sps1

            else -> R.drawable.sps1
        }
    }

    private fun changeUI(generatedBackground : Int) : String{
        return if (generatedBackground == 20 || generatedBackground == 23 || generatedBackground == 25 || generatedBackground == 35){
            "GREEN"
        }else{
            "NOT GREEN"
        }
    }
}