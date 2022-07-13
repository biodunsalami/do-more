package com.example.domore.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domore.R
import com.example.domore.app.DoMoreApp
import com.example.domore.data.Task
import com.example.domore.databinding.FragmentTaskDetailsBinding
import com.example.domore.viewModel.TaskViewModel
import com.example.domore.viewModel.TaskViewModelFactory


class TaskDetailsFragment : SharedFragment() {

    private lateinit var binding : FragmentTaskDetailsBinding

    private lateinit var task : Task

    private val navigationArgs: TaskDetailsFragmentArgs by navArgs()

    private val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            (activity?.application as DoMoreApp).taskDatabase.taskDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.taskId

        viewModel.retrieveTask(id).observe(this.viewLifecycleOwner){selectedTask ->
            task = selectedTask
            bind(task)
        }

        binding.deleteTaskTextView.setOnClickListener {
            viewModel.deleteTask(task)
            findNavController().navigateUp()
        }

    }

    private fun bind(task: Task){
        binding.apply {
            dueDateTextView.text = task.dueDate
            taskRemainderTimeTextView.text = task.remainderTime
            taskDescriptionTextView.text = task.description
            notesTextView.text = task.note
            priorityNumberTextView.text = task.priority.toString()
        }

    }

    private fun toggleViews(){

    }

}