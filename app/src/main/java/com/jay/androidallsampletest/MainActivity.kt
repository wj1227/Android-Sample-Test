package com.jay.androidallsampletest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            throw RuntimeException("zzzzz")
        } catch (e: Exception) {
            Log.d("testest", "onCreate: $e")
        }
    }

    fun materialCalendarView(v: View) {
        val intent = Intent(this, MaterialCalendarViewActivity::class.java)
        startActivity(intent)
    }

    fun mpchart(v: View) {
        val intent = Intent(this, MpChartActivity::class.java)
        startActivity(intent)
    }
}