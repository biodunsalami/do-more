package com.example.domore.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domore.R
import com.example.domore.adapter.TaskInfoInterface
import com.example.domore.adapter.TaskListAdapter
import com.example.domore.app.DoMoreApp
import com.example.domore.data.Task
import com.example.domore.databinding.FragmentTodayBinding
import com.example.domore.viewModel.TaskViewModel
import com.example.domore.viewModel.TaskViewModelFactory

class TodayFragment : SharedFragment(), TaskInfoInterface {

    private lateinit var binding: FragmentTodayBinding

    private lateinit var taskAdapter: TaskListAdapter

    private var todayList = mutableListOf<Task>()

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

        binding.date.text = getString(R.string.current_date, day, monthName, year)

        binding.taskNumInfoTextView.text = getString(R.string.task_number_info,
            viewModel.allTasks.value?.size)

        binding.taskLabel.text = getString(R.string.task_type_header, "Today's")


        binding.fab.setOnClickListener {
            activityCast().setSheetToPeek()
        }

        taskAdapter = TaskListAdapter(this@TodayFragment, requireContext())

//        val action = TodayFragmentDirections.actionTodayFragmentToTaskDetailsFragment(it.id)
//        this.findNavController().navigate(action)

        binding.recyclerView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.allTasks.observe(this.viewLifecycleOwner) { tasks ->
            tasks.let {
                taskAdapter.submitList(it.filter { task -> task.dueDate == dateToday })
            }

        }

    }

    override fun onCardClicked(position: Int) {
        val taskClicked = viewModel.allTasks.value?.get(position)

        val action = taskClicked?.id?.let {
            TodayFragmentDirections.actionTodayFragmentToTaskDetailsFragment(
                it
            )
        }
        if (action != null) {
            this.findNavController().navigate(action)
        }
    }

    override fun onDoneClicked(position: Int, isDone: Boolean) {
        val taskClicked = viewModel.allTasks.value?.get(position)

        updateTask()

        taskClicked?.isFavourite = !isDone
    }

    override fun onFavouriteClicked(position: Int, isFavourite: Boolean) {
        val taskClicked = viewModel.allTasks.value?.get(position)

        taskClicked?.isFavourite = !isFavourite
    }

}