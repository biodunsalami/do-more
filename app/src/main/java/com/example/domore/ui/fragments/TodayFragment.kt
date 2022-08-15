package com.example.domore.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domore.R
import com.example.domore.adapter.TaskInfoInterface
import com.example.domore.adapter.TaskListAdapter
import com.example.domore.databinding.FragmentTodayBinding

class TodayFragment : SharedFragment(), TaskInfoInterface {

    private lateinit var binding: FragmentTodayBinding

    private lateinit var taskAdapter: TaskListAdapter

    private var numOfTaskToday = 0

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
                numOfTaskToday = it.filter { task -> task.dueDate == dateToday }.filter { task -> !task.isDone }.size

                binding.taskNumInfoTextView.text = getString(R.string.task_number_info, numOfTaskToday)


                //progressBar update
                binding.progressBar.max = it.filter { task -> task.dueDate == dateToday }.size
                binding.progressBar.progress = it.filter { task -> task.dueDate == dateToday }.filter { task -> task.isDone }.size

            }

        }

        Log.e("MSG out", "$numOfTaskToday")



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

    override fun onFavouriteClicked(position: Int, isFavourite: Boolean) {
        val taskClicked = viewModel.allTasks.value?.get(position)
        taskClicked?.isFavourite = isFavourite

        if (taskClicked != null){
            viewModel.taskFavourite(taskClicked)
        }
    }

    override fun onDoneClicked(position: Int, isDone: Boolean) {
        val taskClicked = viewModel.allTasks.value?.get(position)
        taskClicked?.isDone = isDone
        Log.e("Position", "$position")

        if (taskClicked != null) {
            viewModel.taskCompletion(taskClicked)
        }

    }

}