package com.example.sportpact

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment

class UniversalFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val id = requireArguments().getInt("index")
        val view = inflater.inflate(id, container, false)

        view.findViewById<TextView>(R.id.learn_more)?.setOnClickListener {
            val intent = Intent(requireActivity(), ActivityLearnMore::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        return view
    }
}