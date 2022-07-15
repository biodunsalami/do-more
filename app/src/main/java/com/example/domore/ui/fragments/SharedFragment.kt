package com.example.domore.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.domore.MainActivity
import com.example.domore.R
import com.example.domore.app.DoMoreApp
import com.example.domore.viewModel.TaskViewModel
import com.example.domore.viewModel.TaskViewModelFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


open class SharedFragment : Fragment() {

    val date = Calendar.getInstance()
    val day = date.get(Calendar.DAY_OF_MONTH)
    val month = date.get(Calendar.MONTH)
    val year = date.get(Calendar.YEAR)

    var monthName = ""

    val dateToday = ""

    fun activityCast() : MainActivity {
        return activity as MainActivity
    }

    val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            (activity?.application as DoMoreApp).taskDatabase.taskDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setMonthName(month)
    }


    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    private fun setMonthName(month: Int) {
        when (month) {
            1 -> {

                monthName = getString(R.string.month1)
            }
            2 -> {
                monthName = getString(R.string.month2)
            }
            3 -> {
                monthName = getString(R.string.month3)
            }
            4 -> {
                monthName = getString(R.string.month4)
            }
            5 -> {
                monthName = getString(R.string.month5)
            }
            6 -> {
                monthName = getString(R.string.month6)
            }
            7 -> {
                monthName = getString(R.string.month7)
            }
            8 -> {
                monthName = getString(R.string.month8)
            }
            9 -> {
                monthName = getString(R.string.month9)
            }
            10 -> {
                monthName = getString(R.string.month10)
            }
            11 -> {
                monthName = getString(R.string.month11)
            }
            12 -> {
                monthName = getString(R.string.month12)
            }
        }

    }


    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            activityCast().binding.taskTitle.text.toString(),
            activityCast().binding.taskDescriptionTextView.text.toString(),
            activityCast().binding.priorityAutocompleteTextview.text.toString(),
        )
    }

    fun addNewTask() {
        if (isEntryValid()) {
            viewModel.addNewTask(
                activityCast().binding.taskTitle.text.toString(),
                activityCast().binding.taskDescriptionTextView.text.toString(),
                activityCast().binding.dueDateEditText.text.toString(),
                activityCast().binding.taskReminderTextView.text.toString(),
                activityCast().binding.additionalInfo.text.toString(),
                activityCast().binding.priorityAutocompleteTextview.text.toString().toInt(),
                activityCast().binding.labelAutocompleteTextview.text.toString(),


            )
            showToast("Task Added Successfully")
            clearFields()
            activityCast().hideSheet()

        }else{
            showToast("Enter fields")
        }

    }


    fun updateTask(){
        if (isEntryValid()) {
            viewModel.updateTask(
                1,
//                this.navigationArgs.itemId,
                activityCast().binding.taskTitle.text.toString(),
                activityCast().binding.taskDescriptionTextView.text.toString(),
                activityCast().binding.dueDateEditText.text.toString(),
                activityCast().binding.taskReminderTextView.text.toString(),
                activityCast().binding.additionalInfo.text.toString(),
                activityCast().binding.priorityAutocompleteTextview.text.toString().toInt(),
                activityCast().binding.labelAutocompleteTextview.text.toString()
            )
        }
    }

    fun clearFields(){
        activityCast().binding.taskTitle.text.clear()
        activityCast().binding.taskDescriptionTextView.text.clear()
        activityCast().binding.dueDateEditText.setText("")
        activityCast().binding.taskReminderTextView.setText("")
        activityCast().binding.additionalInfo.text.clear()
        activityCast().binding.priorityAutocompleteTextview.setText("")
        activityCast().binding.labelAutocompleteTextview.setText("")
    }

}