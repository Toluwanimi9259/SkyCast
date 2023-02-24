package com.techafresh.skycast.presentation.activities.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.techafresh.skycast.R

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        supportActionBar!!.hide()
    }
}