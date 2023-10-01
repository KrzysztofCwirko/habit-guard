package com.example.sportpact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sportpact.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = resources.getColor(R.color.main_color)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val preferences = getPrefs(this)

        with (preferences.edit()) {
            putBoolean(ACTIVITY_DONE_OUTSIDE, false)
            putString(ACTIVITY_TIME, (0.0).toString())
            apply()
        }

        binding.onboardingRV.adapter = MainMenuAdapter(this,
            arrayListOf(
                FragmentMainView(),
                FragmentSettings()
            ))

        binding.settings.setOnClickListener {
            binding.onboardingRV.currentItem = 1
        }
    }
}