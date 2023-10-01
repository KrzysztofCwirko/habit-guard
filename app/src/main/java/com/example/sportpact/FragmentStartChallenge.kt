package com.example.sportpact

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentStartChallenge : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_challenge_accept, container, false)

        val texts = arrayOf(view.findViewById(R.id.c_week),
            view.findViewById(R.id.c_week),view.findViewById(R.id.c_week),view.findViewById<TextView>(R.id.c_week))

        SeekBarFragment.onRefresh = {
                texts.forEachIndexed { index, textView ->
                val new = textView.text.toString()
                if(new.contains("X")) new.replace("X", SeekBarFragment.values[index].toString())
                if(new.contains("Y")) new.replace("Y", if(SeekBarFragment.values[index] == 1) { "" } else {"s"})
                textView.text = new
            }
        }

        view.findViewById<TextView>(R.id.start_ch).setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        return view
    }
}