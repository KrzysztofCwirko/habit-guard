package com.example.sportpact

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val ACTIVITY_TIME = "onv3bv j3rv v 3rv"
const val ACTIVITY_TARGET_TIME = "0ni j3rv v 3rv"
const val ACTIVITY_DEFAULT_TIME = 30.0
const val ACTIVITY_DONE_OUTSIDE = "ewg23gib 3 320fn 10n 1"

fun tickTime(context: Context, onEnd : () -> Unit) : Job {
    return CoroutineScope(Dispatchers.Default).launch {
        while (true) {
            val preferences = getPrefs(context)
            val time = preferences.getString(ACTIVITY_TIME, "0.0")?.toDouble() ?: 0.0

            delay(1000)

            with (preferences.edit()) {
                putString(ACTIVITY_TIME, (time + 1.0).toString())
                apply()
            }

            val targetTime = preferences.getString(ACTIVITY_TARGET_TIME, ACTIVITY_DEFAULT_TIME.toString())?.toDouble() ?: ACTIVITY_DEFAULT_TIME

            if(time == targetTime) {
                onEnd()
                break
            }
        }
    }
}

fun getPrefs(context: Context) : SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
}