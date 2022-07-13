package com.example.domore.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domore.R
import com.example.domore.databinding.FragmentTodayBinding

class TodayFragment : SharedFragment() {

    private lateinit var binding: FragmentTodayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTodayBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.taskLabel.text = getString(R.string.task_type_header, "Today's")

        binding.date.text = getString(R.string.current_date, day, monthName, year)

        binding.fab.setOnClickListener {
            activityCast().binding.bottomSheet.visibility = View.VISIBLE
        }

    }

}