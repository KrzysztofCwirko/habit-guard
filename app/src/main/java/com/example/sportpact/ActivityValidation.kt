package com.example.sportpact

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.sportpact.databinding.ActivityValidationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ActivityValidation : AppCompatActivity() {
    private lateinit var binding: ActivityValidationBinding
    private var timeTick : Job? = null
    private var uiUpdate : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValidationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val sharedPref = getPrefs(this)
        val time = sharedPref.getString(ACTIVITY_TIME, "0.0")?.toDouble() ?: 0.0
        val targetTime = sharedPref.getString(ACTIVITY_TARGET_TIME, ACTIVITY_DEFAULT_TIME.toString())?.toDouble() ?: ACTIVITY_DEFAULT_TIME

        with (sharedPref.edit()) {
            putString(ACTIVITY_TIME, (0.0).toString())
            apply()
        }

        val doneOutside = sharedPref.getBoolean(ACTIVITY_DONE_OUTSIDE, false)
        if(doneOutside) {
            exerciseDone()
            return
        }

        binding.progressBar.setProgress(time, targetTime)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            cancelOnGoingValidation()
            startService()
        } else {
            notCompatible()
        }

        updateUI()
    }

    override fun onPause() {
        super.onPause()

       if (!checkIfIsCompatible()){
            timeTick?.cancel()
        }

        uiUpdate?.cancel()
    }

    override fun onResume() {
        super.onResume()

        if (!checkIfIsCompatible()){
            timeTick = tickTime(this) {
                exerciseDone()
            }
        }

        updateUI()
    }

    private fun cancelOnGoingValidation() : Boolean {
        stopService()
        return true
    }

    private fun checkIfIsCompatible() : Boolean {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        return sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) == null
    }

    private fun startService() {
        val serviceIntent = Intent(this, StepsService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun stopService() {
        val serviceIntent = Intent(this, StepsService::class.java)
        stopService(serviceIntent)
    }

    private fun notCompatible() {
        validateTimeOnly()
    }

    private fun validateTimeOnly() {
        timeTick?.cancel()
        timeTick = tickTime(this) {
            exerciseDone()
        }
    }

    private fun updateUI() {
        uiUpdate?.cancel()
        uiUpdate = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                val preferences = getPrefs(this@ActivityValidation)
                val time = preferences.getString(ACTIVITY_TIME, "0.0")?.toDouble() ?: 0.0
                val targetTime = preferences.getString(ACTIVITY_TARGET_TIME, ACTIVITY_DEFAULT_TIME.toString())?.toDouble() ?: ACTIVITY_DEFAULT_TIME

                binding.progressBar.setProgress(targetTime - time, targetTime)

                if(targetTime == time) {
                    exerciseDone()
                    break
                }

                delay(100)
            }
        }
    }

    private fun exerciseDone() {
        if(!checkIfIsCompatible()){
            timeTick?.cancel()
        } else {
            cancelOnGoingValidation()
        }

        uiUpdate?.cancel()

        val preferences = getPrefs(this)

        with (preferences.edit()) {
            putBoolean(ACTIVITY_DONE_OUTSIDE, true)
            apply()
        }


    }
}