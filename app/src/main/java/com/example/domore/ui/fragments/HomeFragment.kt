package com.example.domore.ui.fragments

import android.app.DatePickerDialog
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


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

//    val date = LocalDate.now()
    var calender = Calendar.getInstance()

    private var dueDate = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 350
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpDropDownEntryFields()

        binding.addNewTask.setOnClickListener {
            binding.bottomSheet.visibility = View.VISIBLE
        }


    }




    private fun setUpDropDownEntryFields(){
        val labels = resources.getStringArray(R.array.labels)
        val labelArrayAdapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, labels)
        binding.labelAutocompleteTextview.setAdapter(labelArrayAdapter)

        val priorities = resources.getStringArray(R.array.priority)
        val nationalityArrayAdapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, priorities)
        binding.priorityAutocompleteTextview.setAdapter(nationalityArrayAdapter)


        //Override OnResume and call this function in there to make the drop down items persist
    }


}