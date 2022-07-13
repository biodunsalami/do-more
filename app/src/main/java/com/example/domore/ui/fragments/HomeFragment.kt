package com.example.domore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domore.R
import com.example.domore.adapter.TaskListAdapter
import com.example.domore.app.DoMoreApp
import com.example.domore.data.Task
import com.example.domore.databinding.FragmentHomeBinding
import com.example.domore.viewModel.TaskViewModel
import com.example.domore.viewModel.TaskViewModelFactory


class HomeFragment : SharedFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            (activity?.application as DoMoreApp).taskDatabase.taskDao()
        )
    }

    private lateinit var taskAdapter: TaskListAdapter

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


//        currentProgress += 10
//        binding.progressBar.max = 100 //change this to size of adapter or sm
//        binding.progressBar.progress = currentProgress

        binding.date.text = getString(R.string.current_date, day, monthName, year)

        binding.addNewTask.setOnClickListener {
            activityCast().binding.bottomSheet.visibility = View.VISIBLE
        }

        binding.fab.setOnClickListener {
            activityCast().binding.bottomSheet.visibility = View.VISIBLE
        }

       activityCast().binding.doneButton.setOnClickListener {
           addNewTask()
       }

        taskAdapter = TaskListAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToTaskDetailsFragment(it.id)
            this.findNavController().navigate(action)
        }

        binding.listRecyclerView.apply {
          adapter = taskAdapter
          layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.allTasks.observe(this.viewLifecycleOwner) {tasks ->
            tasks.let {
                taskAdapter.submitList(it)
            }

            if(viewModel.allTasks.value?.isNotEmpty() == true){
                binding.addNewTask.visibility = View.INVISIBLE
                binding.noTaskTextView.visibility = View.INVISIBLE
                binding.noTaskAddedBgImageView.visibility = View.INVISIBLE

                binding.fab.visibility = View.VISIBLE
                binding.addTaskLabel.visibility = View.VISIBLE
            }

        }

        //"You have xx Task to do
        binding.taskNumInfoTextView.text = getString(R.string.task_number_info, taskAdapter.itemCount)

    }


    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            activityCast().binding.taskTitle.text.toString(),
            activityCast().binding.taskDescriptionTextView.text.toString(),
            activityCast().binding.priorityAutocompleteTextview.text.toString(),
        )
    }



    private fun bind(task: Task) {
        activityCast().binding.apply {
            taskTitle.setText(task.title, TextView.BufferType.SPANNABLE)
            taskDescriptionTextView.setText(task.description, TextView.BufferType.SPANNABLE)
            dueDateEditText.setText(task.dueDate, TextView.BufferType.SPANNABLE)
            taskReminderTextView.setText(task.remainderTime, TextView.BufferType.SPANNABLE)
            additionalInfo.setText(task.note, TextView.BufferType.SPANNABLE)
            priorityAutocompleteTextview.setText(task.priority, TextView.BufferType.SPANNABLE)
            labelAutocompleteTextview.setText(task.label, TextView.BufferType.SPANNABLE)

            doneButton.setOnClickListener { updateTask() }
        }
    }

    private fun addNewTask() {
        if (isEntryValid()) {
            viewModel.addNewTask(
                activityCast().binding.taskTitle.text.toString(),
                activityCast().binding.taskDescriptionTextView.text.toString(),
                activityCast().binding.dueDateEditText.text.toString(),
                activityCast().binding.taskReminderTextView.text.toString(),
                activityCast().binding.additionalInfo.text.toString(),
                activityCast().binding.priorityAutocompleteTextview.text.toString().toInt(),
                activityCast().binding.labelAutocompleteTextview.text.toString()
            )
            showToast("Task Added Successfully")
            clearFields()
            activityCast().hideSheet()

        }else{
            showToast("Enter fields")
        }

    }

    private fun updateTask(){

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

    private fun clearFields(){
        activityCast().binding.taskTitle.text.clear()
        activityCast().binding.taskDescriptionTextView.text.clear()
        activityCast().binding.dueDateEditText.setText("")
        activityCast().binding.taskReminderTextView.setText("")
        activityCast().binding.additionalInfo.text.clear()
        activityCast().binding.priorityAutocompleteTextview.setText("")
        activityCast().binding.labelAutocompleteTextview.setText("")
    }

//    private fun toggleViews() {
//        if (taskAdapter.itemCount > 1) {
//            binding.addNewTask.visibility = View.INVISIBLE
//            binding.noTaskTextView.visibility = View.INVISIBLE
//            binding.noTaskAddedBgImageView.visibility = View.INVISIBLE
//
//            binding.fab.visibility = View.VISIBLE
//            binding.addTaskLabel.visibility = View.VISIBLE
//
//        }
//    }

}