package com.example.sportpact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sportpact.databinding.ActivityValidationBinding

class ActivityValidation : AppCompatActivity() {
    private lateinit var binding: ActivityValidationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValidationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}