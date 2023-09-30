package com.example.sportpact

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class StepsService : Service() {
    private val CHANNEL_ID = "ForegroundServiceChannel"
    private lateinit var sensorManager : SensorManager
    private lateinit var stepCounter : Sensor
    private var job : Job? = null
    private var timeTick : Job? = null
    private var NotId = 1

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        requestSensor()

        timeTick?.cancel()
        timeTick = tickTime(this) {
            completeTask()
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(NotId, getMyNotification("We are checking on you ;)"))

        return START_NOT_STICKY
    }

    private fun getMyNotification(text: String) : Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setOngoing(true)
            .setContentTitle("Activity check")
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setSmallIcon(com.google.android.material.R.drawable.abc_btn_radio_material)
            .build()
    }

    private fun requestSensor() {
        sensorManager.requestTriggerSensor(object : TriggerEventListener() {
            override fun onTrigger(ev: TriggerEvent?) {
                checkForUserActivity(ev)
            }
        }, stepCounter)

        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            delay(11000)
             requestSensor()
        }
    }

    private fun completeTask() {
        job?.cancel()
        timeTick?.cancel()

        val preferences = getPrefs(this)

        with (preferences.edit()) {
            putBoolean(ACTIVITY_DONE_OUTSIDE, true)
            apply()
        }

        doneNotification()
    }

    private fun checkForUserActivity(ev: TriggerEvent?) {

    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Foreground Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(
            NotificationManager::class.java
        )
        manager.createNotificationChannel(serviceChannel)
    }

    private fun doneNotification() {
        val text = "Done exercising! Go back to our app?"
        val notification: Notification = getMyNotification(text)
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(NotId, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        // Here we do not use a binder to interact with the foreground service.
        return null
    }
}