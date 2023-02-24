package com.techafresh.skycast.presentation.activities.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.techafresh.skycast.MainActivity
import com.techafresh.skycast.R
import com.techafresh.skycast.presentation.activities.onboarding.OnboardingActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()

        Handler().postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity , OnboardingActivity::class.java))
            finish()
        }, 3000)
    }
}