package com.example.sportpact

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenSlidePagerAdapter(fa: FragmentActivity, private val fragments : ArrayList<Int>) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return UniversalFragment().apply {
            arguments = bundleOf(
                Pair(
                    "index", fragments[position]
                )
            )
        }
    }
}