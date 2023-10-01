package com.example.sportpact

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.sportpact.databinding.ActivityChallangeBinding

class ChallengeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChallangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallangeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dots = arrayListOf(binding.imageViewDot1, binding.imageViewDot2,
            binding.imageViewDot3, binding.imageViewDot4, binding.imageViewDot5)

        binding.onboardingRV.offscreenPageLimit = 5

        binding.onboardingRV.adapter = ScreenSeekBarFragment(this, arrayListOf(
            Triple(12, 1, 1),    //max, min, step
            Triple(7, 1, 1),
            Triple(60, 5, 5),
            Triple(200, 20, 5),
        ), arrayListOf(
            Pair("I want to train for", "weekX."),
            Pair("I want to train","timeX a week."),
            Pair("I want to train for","minuteX a day."),
            Pair("I commit with", "PLN"),
        )
        )

        binding.onboardingRV.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if(position == 0) {
                    binding.leftArrow.visibility = View.INVISIBLE
                } else {
                    binding.leftArrow.visibility = View.VISIBLE
                }

                if(position == binding.onboardingRV.adapter!!.itemCount-1) {
                    binding.rightArrow.visibility = View.INVISIBLE
                    binding.leftArrow.imageTintList = ColorStateList.valueOf(getColor(R.color.white))
                } else {
                    binding.rightArrow.visibility = View.VISIBLE
                    binding.leftArrow.imageTintList = ColorStateList.valueOf(getColor(R.color.main_color))
                }

                dots[position].imageTintList = ColorStateList.valueOf(getColor(R.color.main_color))
                dots.filter { d -> d != dots[position] }.forEach {
                    it.imageTintList = ColorStateList.valueOf(getColor(R.color.dot_deselected))
                }
                super.onPageSelected(position)
            }
        })

        binding.leftArrow.setOnClickListener {
            if(binding.onboardingRV.currentItem == 0) {
                return@setOnClickListener
            }
            binding.onboardingRV.currentItem -= 1
        }

        binding.rightArrow.setOnClickListener {
            if(binding.onboardingRV.currentItem < binding.onboardingRV.adapter!!.itemCount - 1) {
                binding.onboardingRV.currentItem += 1
                return@setOnClickListener
            }
        }
    }
}