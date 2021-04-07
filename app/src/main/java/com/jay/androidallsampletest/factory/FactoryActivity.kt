package com.jay.androidallsampletest.factory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.jay.androidallsampletest.R

class FactoryActivity : AppCompatActivity() {
    private lateinit var frame: FrameLayout
    private lateinit var fragment: Fragment
    private lateinit var btnFirst: Button
    private lateinit var btnSecond: Button
    private lateinit var btnThird: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = FragmentFactoryImpl(100)

        setContentView(R.layout.activity_factory)

        initView()
        initListener()
    }

    private fun initView() {
        frame = findViewById(R.id.frame_layout)
        btnFirst = findViewById(R.id.btn_first)
        btnSecond = findViewById(R.id.btn_second)
        btnThird = findViewById(R.id.btn_third)
    }

    private fun initListener() {
        btnFirst.setOnClickListener {
            fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, FirstFragment::class.java.name)
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commitNow()
        }
        btnSecond.setOnClickListener {
            fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, SecondeFragment::class.java.name)
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commitNow()
        }
        btnThird.setOnClickListener {
            fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, ThirdFragment::class.java.name)
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commitNow()
        }
    }
}