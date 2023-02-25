package com.techafresh.skycast.presentation.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.techafresh.skycast.R
import com.techafresh.skycast.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    lateinit var binding : FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding.textView7Days.setOnClickListener{
            it.findNavController().navigate(R.id.action_mainFragment_to_daysFragment)
        }
    }
}