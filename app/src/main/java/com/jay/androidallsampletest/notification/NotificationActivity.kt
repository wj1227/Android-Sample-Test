package com.jay.androidallsampletest.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.jay.androidallsampletest.R
import com.jay.androidallsampletest.toolbartest.ToolbarTestActivity

class NotificationActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    private val createNotification: Button by lazy {
        findViewById(R.id.btn_create_notification)
    }

    private val clickNotification: Button by lazy {
        findViewById(R.id.btn_click_notification)
    }

    private val actionNotification: Button by lazy {
        findViewById(R.id.btn_notification_btn_add)
    }

    private val replyNotification: Button by lazy {
        findViewById(R.id.btn_reply_notification)
    }

//    private val receiver: JayBroadcastReceiver by lazy {
//        JayBroadcastReceiver()
//    }
//
//    private val intentFilter: IntentFilter by lazy {
//        IntentFilter().apply {
//            addAction(Intent.ACTION_POWER_CONNECTED)
//            addAction(Intent.ACTION_POWER_DISCONNECTED)
//            //addAction("action!!!!")
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        initClickListener()
        createChannel()

        val text = getMessageText(intent)
        if (!text.isNullOrEmpty()) {
            Toast.makeText(this, "$text", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        //registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        //unregisterReceiver(receiver)
    }

    private fun initClickListener() {
        createNotification.setOnClickListener {
            create()
        }
        clickNotification.setOnClickListener {
            click()
        }
        actionNotification.setOnClickListener {
            action()
        }
        replyNotification.setOnClickListener {
            reply()
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

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            0
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_arrow_right)
            .setContentTitle("click notification")
            .setContentText("where~~~")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notify(builder)
    }

    private fun action() {
        val intent = Intent(this, JayBroadcastReceiver::class.java).apply {
            action = "noti"
            putExtra("notifi", 9999)
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.sample_icon_3)
            .setContentTitle("action title")
            .setContentText("action description!!!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_launcher_foreground, "add button", pendingIntent)

        notify(builder)
    }

    private fun reply() {
        val replyLabel = "please your message"
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(replyLabel)
            build()
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val action: NotificationCompat.Action = NotificationCompat.Action.Builder(
            0,
            "TITLE!",
            pendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()

        val action2: NotificationCompat.Action = NotificationCompat.Action.Builder(
            R.drawable.ic_launcher_foreground,
            getString(R.string.app_name),
            pendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_arrow_left)
            .setContentTitle("reply title")
            .setContentText("reply description!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(action)
            .addAction(action2)

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

    private fun getMessageText(intent: Intent): CharSequence? {
        return RemoteInput.getResultsFromIntent(intent)?.getCharSequence(KEY_TEXT_REPLY)
    }

    companion object {
        const val CHANNEL_ID = "1"
        const val NOTIFICATION_ID = 100

        const val KEY_TEXT_REPLY = "key_text_reply"
    }
}