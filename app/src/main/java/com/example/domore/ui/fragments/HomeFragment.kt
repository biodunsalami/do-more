package com.example.domore.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.domore.R
import com.example.domore.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class HomeFragment : SharedFragment() {

    private lateinit var binding: FragmentHomeBinding

    private var currentProgress = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//
//        currentProgress += 10
//        binding.progressBar.max = 100 //change this to size of adapter or sm
//        binding.progressBar.progress = currentProgress

        binding.addNewTask.setOnClickListener {
            activityCast().binding.bottomSheet.visibility = View.VISIBLE
        }



    }


}