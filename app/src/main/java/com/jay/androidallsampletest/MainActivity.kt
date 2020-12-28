package com.jay.androidallsampletest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jay.androidallsampletest.coordinator.CoordinatorActivity
import com.jay.androidallsampletest.rx.RxJavaActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun materialCalendarView(v: View) {
        val intent = Intent(this, MaterialCalendarViewActivity::class.java)
        startActivity(intent)
    }

    fun mpchart(v: View) {
        val intent = Intent(this, MpChartActivity::class.java)
        startActivity(intent)
    }

    fun rxjava(v: View) {
        val intent = Intent(this, RxJavaActivity::class.java)
        startActivity(intent)
    }

    fun activity(v: View) {
        val uri = Uri.parse("doc://url.com")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    fun coordinator(v: View) {
        val intent = Intent(this, CoordinatorActivity::class.java)
        startActivity(intent)
    }
}