package com.example.domore.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domore.R
import com.example.domore.adapter.TaskListAdapter
import com.example.domore.data.Task
import com.example.domore.databinding.FragmentHomeBinding


class HomeFragment : SharedFragment() {

    private lateinit var binding: FragmentHomeBinding

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
            activityCast().setSheetToPeek()
        }

        binding.fab.setOnClickListener {
            activityCast().setSheetToPeek()
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
                Log.e("The List", "$it")
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
        binding.taskNumInfoTextView.text = getString(R.string.task_number_info,
            viewModel.allTasks.value?.size)

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

}