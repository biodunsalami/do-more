package com.example.domore.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import com.example.domore.R
import com.example.domore.databinding.FragmentLabelDetailsBinding


class LabelDetailsFragment : SharedFragment() {

    private lateinit var binding: FragmentLabelDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLabelDetailsBinding.inflate(inflater, container, false)

        activityCast().supportActionBar?.hide()


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.taskTypeLabel.text =
            getString(R.string.task_type_header, activityCast().currentLabel.labelName)

        binding.fab.setOnClickListener {
            activityCast().binding.bottomSheet.visibility = View.VISIBLE
        }

    }

    override fun onStop() {
        super.onStop()

        activityCast().supportActionBar?.show()

    }


}