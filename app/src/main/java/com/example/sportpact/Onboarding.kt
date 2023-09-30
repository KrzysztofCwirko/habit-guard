package com.example.sportpact

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.sportpact.databinding.ActivityOnboardingBinding

class Onboarding : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.onboardingRV.adapter = ScreenSlidePagerAdapter(this)
        binding.onboardingRV.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if(position == 0) {
                    binding.imageViewDot1.imageTintList = ColorStateList.valueOf(getColor(R.color.main_color))
                    binding.imageViewDot2.imageTintList = ColorStateList.valueOf(getColor(R.color.dot_deselected))
                } else {
                    binding.imageViewDot1.imageTintList = ColorStateList.valueOf(getColor(R.color.dot_deselected))
                    binding.imageViewDot2.imageTintList = ColorStateList.valueOf(getColor(R.color.main_color))
                }
                super.onPageSelected(position)
            }
        })
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment = when(position){
            0 -> IntroFragment1()
            else -> IntroFragment2()
        }
    }
}