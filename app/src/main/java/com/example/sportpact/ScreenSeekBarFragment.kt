package com.example.sportpact

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenSeekBarFragment(fa: FragmentActivity, private val seekBarSetting : ArrayList<Triple<Int,Int,Int>>, private val names : ArrayList<Pair<String, String>>) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = seekBarSetting.size
    override fun createFragment(position: Int): Fragment {
        if(position == itemCount-1) {
            return FragmentStartChallenge()
        }

        return SeekBarFragment().apply {
            arguments = bundleOf(
                Pair(
                    "settings", intArrayOf(seekBarSetting[position].first, seekBarSetting[position].second, seekBarSetting[position].third)
                ),
                Pair(
                    "name",  arrayOf(names[position].first, names[position].second)
                    ),
                Pair(
                    "position",  position
                    )
            )
        }
    }
}