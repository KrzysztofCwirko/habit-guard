package com.example.sportpact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment

class SeekBarFragment : Fragment() {
    companion object {
        val values : ArrayList<Int> = ArrayList()
        var onRefresh : ()->Unit = {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val id = requireArguments().getIntArray("settings")!!
        val name = requireArguments().getStringArray("name")!!
        val position = requireArguments().getInt("position")
        val view = inflater.inflate(R.layout.fragment_creator_step_days, container, false)

        if(values.size <= position) {
            values.add(id[1])
        }

        val seek = view.findViewById<SeekBar>(R.id.seekBar)
        seek.max = id[0]
        seek.min = id[1]
        seek.incrementProgressBy(id[2])
        val text = view.findViewById<TextView>(R.id.sliderInput)

        view.findViewById<TextView>(R.id.titleE).text = name[0]

        seek.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val content = "$p1 ${
                    if(name[1].contains("X")){
                        name[1].replace("X", if(p1 == 1) { "" } else { "s" })
                    } else {
                        name[1]
                    }
                }"
                text.text = content
                values[position] = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                onRefresh()
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
                onRefresh()
            }
        })

        seek.progress = values[position]
        onRefresh()

        return view
    }
}