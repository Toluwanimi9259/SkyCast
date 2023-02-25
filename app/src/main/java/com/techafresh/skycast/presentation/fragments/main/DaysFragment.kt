package com.techafresh.skycast.presentation.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.techafresh.skycast.R
import com.techafresh.skycast.databinding.FragmentDaysBinding


class DaysFragment : Fragment() {

    lateinit var binding: FragmentDaysBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_days, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDaysBinding.bind(view)

        binding.imageViewBack.setOnClickListener {
            it.findNavController().navigate(R.id.action_daysFragment_to_mainFragment)
        }

    }
}