package com.techafresh.skycast.presentation.fragments.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techafresh.skycast.MainActivity
import com.techafresh.skycast.R
import com.techafresh.skycast.databinding.FragmentScreen3Binding
import com.techafresh.skycast.presentation.activities.onboarding.OnboardingActivity


class Screen3Fragment : Fragment() {

    lateinit var binding : FragmentScreen3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_screen3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScreen3Binding.bind(view)
        binding.continueButton.setOnClickListener {
            startActivity(Intent(activity , MainActivity::class.java))
        }
    }
}