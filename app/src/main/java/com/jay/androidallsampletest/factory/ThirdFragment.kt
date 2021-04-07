package com.jay.androidallsampletest.factory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jay.androidallsampletest.R

class ThirdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("get: ${arguments?.getInt("test")}")
        return inflater.inflate(R.layout.fragment_factory_third, container, false)
    }
}