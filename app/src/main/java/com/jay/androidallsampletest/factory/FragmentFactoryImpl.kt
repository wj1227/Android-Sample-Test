package com.jay.androidallsampletest.factory

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class FragmentFactoryImpl(private val age: Int) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            FirstFragment::class.java.name -> {
                FirstFragment()
            }
            SecondeFragment::class.java.name -> {
                SecondeFragment(age)
            }
            ThirdFragment::class.java.name -> {
                ThirdFragment().apply {
                    arguments = Bundle().apply {
                        putInt("test", age)
                    }
                }
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}