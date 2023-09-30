package com.example.sportpact

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import res.layout.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2


class OnboardingRecyclerView internal constructor(
        context: Context?,
    ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        private val activity_onboarding = 1
        private val activity_onboarding_2 = 2


    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            activity_onboarding
        } else {
                activity_onboarding_2
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder.itemViewType) {
                activity_onboarding -> {
                }

                activity_onboarding_2 -> {
                }
            }
        }
        class ViewHolderOne(itemView: View) : RecyclerView.ViewHolder(itemView) {
            // ViewHolder for activity_onboarding
        }

        class ViewHolderTwo(itemView: View) : RecyclerView.ViewHolder(itemView) {
            // ViewHolder for activity_onboarding_2
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                activity_onboarding -> {
                    val view = LayoutInflater.from(parent.context).inflate((R.layout.fragment_onboarding), parent, false)
                    ViewHolderOne(view)

                }
                activity_onboarding_2 -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_onboarding_2, parent, false)
                    ViewHolderTwo(view)
                }
                else -> throw IllegalArgumentException("Invalid view type")
            }
        }
}