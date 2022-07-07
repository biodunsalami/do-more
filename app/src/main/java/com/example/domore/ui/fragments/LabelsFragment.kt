package com.example.domore.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domore.MainActivity
import com.example.domore.R
import com.example.domore.databinding.FragmentLabelDetailsBinding
import com.example.domore.databinding.FragmentLabelsBinding


class LabelsFragment : SharedFragment() {

    private lateinit var binding: FragmentLabelsBinding

    private var currentLabel = MainActivity.Label.GENERAL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLabelsBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.generalTextView.setOnClickListener {
            activityCast().changeActivityFragment(
                LabelsFragmentDirections.actionLabelsFragmentToLabelDetailsFragment())

            currentLabel = MainActivity.Label.GENERAL
            Log.e("currentLabel", "$currentLabel")
        }

        binding.personalTextView.setOnClickListener {
            activityCast().changeActivityFragment(
                LabelsFragmentDirections.actionLabelsFragmentToLabelDetailsFragment())

            currentLabel = MainActivity.Label.PERSONAL
            Log.e("currentLabel", "$currentLabel")

        }
        binding.workTextView.setOnClickListener {
            activityCast().changeActivityFragment(
                LabelsFragmentDirections.actionLabelsFragmentToLabelDetailsFragment())

            currentLabel = MainActivity.Label.WORK
            Log.e("currentLabel", "$currentLabel")
        }

        binding.schoolTextView.setOnClickListener {
            activityCast().changeActivityFragment(
                LabelsFragmentDirections.actionLabelsFragmentToLabelDetailsFragment())

            currentLabel = MainActivity.Label.SCHOOL
            Log.e("currentLabel", "$currentLabel")
        }

        binding.homeTextView.setOnClickListener {
            activityCast().changeActivityFragment(
                LabelsFragmentDirections.actionLabelsFragmentToLabelDetailsFragment())

            currentLabel = MainActivity.Label.HOME
            Log.e("currentLabel", "$currentLabel")
        }

        binding.otherTextView.setOnClickListener {
            activityCast().changeActivityFragment(
                LabelsFragmentDirections.actionLabelsFragmentToLabelDetailsFragment())

            currentLabel = MainActivity.Label.OTHERS
            Log.e("currentLabel", "$currentLabel")

        }

        when(currentLabel){
            MainActivity.Label.GENERAL -> binding.generalTextView.setBackgroundColor(resources.getColor(R.color.light_blue))
            MainActivity.Label.PERSONAL -> binding.personalTextView.setBackgroundColor(resources.getColor(R.color.light_blue))
            MainActivity.Label.WORK -> binding.workTextView.setBackgroundColor(resources.getColor(R.color.light_blue))
            MainActivity.Label.SCHOOL -> binding.schoolTextView.setBackgroundColor(resources.getColor(R.color.light_blue))
            MainActivity.Label.HOME -> binding.homeTextView.setBackgroundColor(resources.getColor(R.color.light_blue))
            MainActivity.Label.OTHERS -> binding.otherTextView.setBackgroundColor(resources.getColor(R.color.light_blue))
        }

    }

}