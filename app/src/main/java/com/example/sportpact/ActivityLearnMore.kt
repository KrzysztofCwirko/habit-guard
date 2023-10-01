package com.example.sportpact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sportpact.databinding.ActivityLearnMoreBinding

class ActivityLearnMore : AppCompatActivity()  {
    private lateinit var binding: ActivityLearnMoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = resources.getColor(R.color.main_color)
        super.onCreate(savedInstanceState)
        binding = ActivityLearnMoreBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.backButton.setOnClickListener {
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
            finish()
        }
    }
}