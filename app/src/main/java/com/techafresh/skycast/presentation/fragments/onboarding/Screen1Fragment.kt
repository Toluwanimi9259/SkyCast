package com.techafresh.skycast.presentation.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.techafresh.skycast.R
import com.techafresh.skycast.databinding.FragmentScreen1Binding


class Screen1Fragment : Fragment() {

    lateinit var binding : FragmentScreen1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_screen1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScreen1Binding.bind(view)

        binding.nextButton!!.setOnClickListener {
            it.findNavController().navigate(R.id.action_screen1Fragment_to_screen2Fragment)
        }
    }
}