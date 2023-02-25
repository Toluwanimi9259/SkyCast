package com.techafresh.skycast.presentation.activities.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.techafresh.skycast.MainActivity
import com.techafresh.skycast.R
import com.techafresh.skycast.presentation.activities.onboarding.OnboardingActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()

        sharedPreferences = this.getSharedPreferences("First_Timer_Checker" , Context.MODE_PRIVATE)

        Handler().postDelayed(Runnable {
            checkFirstTimer()
            finish()
        }, 3000)

    }

    private fun checkFirstTimer(){
        if (!sharedPreferences.getBoolean("isFirstTimer" , true)){
            startActivity(Intent(this@SplashActivity , MainActivity::class.java))
        }else{
            startActivity(Intent(this@SplashActivity , OnboardingActivity::class.java))
        }
    }
}