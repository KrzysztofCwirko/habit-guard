package com.example.sportpact

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.sportpact.databinding.ActivityOnboardingBinding


class Onboarding : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.onboardingRV.adapter = ScreenSlidePagerAdapter(this, arrayListOf
            (R.layout.fragment_onboarding, R.layout.fragment_onboarding_2))
        binding.onboardingRV.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if(position == 0) {
                    binding.leftArrow.visibility = View.INVISIBLE
                    binding.imageViewDot1.imageTintList = ColorStateList.valueOf(getColor(R.color.main_color))
                    binding.imageViewDot2.imageTintList = ColorStateList.valueOf(getColor(R.color.dot_deselected))
                } else {
                    binding.leftArrow.visibility = View.VISIBLE
                    binding.imageViewDot1.imageTintList = ColorStateList.valueOf(getColor(R.color.dot_deselected))
                    binding.imageViewDot2.imageTintList = ColorStateList.valueOf(getColor(R.color.main_color))
                }
                super.onPageSelected(position)
            }
        })

        binding.leftArrow.setOnClickListener {
            binding.onboardingRV.currentItem = 0
        }

        binding.rightArrow.setOnClickListener {
            //skip
            if(binding.onboardingRV.currentItem == 0) {
                binding.onboardingRV.currentItem = 1
                return@setOnClickListener
            }
            val intent = Intent(this, ChallengeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}