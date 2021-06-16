package com.jay.androidallsampletest.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.jay.androidallsampletest.R
import com.jay.androidallsampletest.toolbartest.ToolbarTestActivity

class NotificationActivity : AppCompatActivity() {

    private val createNotification: Button by lazy {
        findViewById(R.id.btn_create_notification)
    }

    private val clickNotification: Button by lazy {
        findViewById(R.id.btn_click_notification)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        initClickListener()
        createChannel()
    }

    private fun initClickListener() {
        createNotification.setOnClickListener {
            create()
        }
        clickNotification.setOnClickListener {
            click()
        }
    }

    private fun create() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_arrow_left)
            .setContentTitle("hello")
            .setContentText("hello every one")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notify(builder)
    }

    private fun click() {
        val intent = Intent(this, ToolbarTestActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_arrow_right)
            .setContentTitle("click notification")
            .setContentText("where~~~")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notify(builder)
    }

    private fun notify(builder: NotificationCompat.Builder) {
        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val descriptionText = "channel_description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "1"
        const val NOTIFICATION_ID = 100
    }
}