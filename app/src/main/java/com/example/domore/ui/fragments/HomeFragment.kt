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
import com.example.domore.adapter.TaskInfoInterface
import com.example.domore.adapter.TaskListAdapter
import com.example.domore.data.Task
import com.example.domore.databinding.FragmentHomeBinding


class HomeFragment : SharedFragment(), TaskInfoInterface {

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

        taskAdapter = TaskListAdapter(this@HomeFragment, requireContext())
        //            val action = HomeFragmentDirections.actionHomeFragmentToTaskDetailsFragment(it.id)
        //            this.findNavController().navigate(action)

        binding.listRecyclerView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.allTasks.observe(this.viewLifecycleOwner) {tasks ->
            tasks.let {
                taskAdapter.submitList(it)
                Log.e("The List", "$it")

                binding.taskNumInfoTextView.text = getString(R.string.task_number_info,
                    it.filter { task -> !task.isDone }.size)


                //progressBar update
                binding.progressBar.max = it.size
                binding.progressBar.progress = it.filter { task -> task.isDone }.size

            }

            if(viewModel.allTasks.value?.isNotEmpty() == true){
                binding.addNewTask.visibility = View.INVISIBLE
                binding.noTaskTextView.visibility = View.INVISIBLE
                binding.noTaskAddedBgImageView.visibility = View.INVISIBLE

                binding.fab.visibility = View.VISIBLE
                binding.addTaskLabel.visibility = View.VISIBLE
            }
        }


        currentProgress += 10
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

    override fun onCardClicked(position: Int) {
        val taskClicked = viewModel.allTasks.value?.get(position)

        val action = taskClicked?.id?.let {
            HomeFragmentDirections.actionHomeFragmentToTaskDetailsFragment(
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

        if (taskClicked != null) {
            viewModel.taskCompletion(taskClicked)
        }
    }



}