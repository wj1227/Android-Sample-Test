package com.jay.androidallsampletest.lifecycle

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.jay.androidallsampletest.R
import kotlinx.android.synthetic.main.item_coordinator.*

class LifecycleActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private lateinit var btnNoti: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
        Log.d(TAG, "onCreate: ")

        toast()
        dialog()
        Handler(Looper.getMainLooper()).postDelayed({
            //startActivity(Intent(this, LifecycleActivity2::class.java))
        }, 4000L)

        createNotification()
        btnNoti = findViewById(R.id.btn_noti)
        btnNoti.setOnClickListener {
            notification()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: ")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState: ")
    }

    private fun toast() {
        Toast.makeText(this, "toast", Toast.LENGTH_SHORT).show()
    }

    private fun dialog() {
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("Title")
            setMessage("Message")
            setPositiveButton("ok") { _, _ ->

            }
            setNegativeButton("no") { _, _ ->

            }
        }
        dialog.show()
    }

    private fun notification() {
        val notification = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.sample_icon_1)
            .setContentTitle("title")
            .setContentText("text")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
            .setAutoCancel(true)

        NotificationManagerCompat.from(this).notify(0, notification.build())
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "desc"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("CHANNEL_ID", name, importance)
                .apply {
                    description = descriptionText
                }

            val notificationManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}