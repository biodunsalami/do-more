package com.example.domore.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domore.R
import com.example.domore.adapter.TaskInfoInterface
import com.example.domore.adapter.TaskListAdapter
import com.example.domore.data.Task
import com.example.domore.databinding.FragmentLabelDetailsBinding


class LabelDetailsFragment : SharedFragment(), TaskInfoInterface {

    private lateinit var binding: FragmentLabelDetailsBinding

    private lateinit var taskAdapter: TaskListAdapter

    private var labelList = mutableListOf<Task>()

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
            activityCast().setSheetToPeek()
        }

        taskAdapter = TaskListAdapter(this@LabelDetailsFragment, requireContext())


        binding.recyclerView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.allTasks.observe(this.viewLifecycleOwner) {tasks ->
            tasks.let {
                taskAdapter.submitList(it.filter { task ->
                    task.label == activityCast().currentLabel.labelName })
            }

            if(viewModel.allTasks.value?.isNotEmpty() == true){
                binding.noTaskLabel.visibility = View.INVISIBLE
                binding.noTaskBg.visibility = View.INVISIBLE
            }

        }

    }

    override fun onStop() {
        super.onStop()

        activityCast().supportActionBar?.show()

    }

    override fun onCardClicked(position: Int) {
        val taskClicked = viewModel.allTasks.value?.get(position)

        val action = taskClicked?.id?.let {
            LabelDetailsFragmentDirections.actionLabelDetailsFragmentToTaskDetailsFragment(
                it
            )
        }
        if (action != null) {
            this.findNavController().navigate(action)
        }
    }

    override fun onDoneClicked(position: Int, isDone: Boolean) {
        val taskClicked = viewModel.allTasks.value?.get(position)

        taskClicked?.isDone = !isDone
    }

    override fun onFavouriteClicked(position: Int, isFavourite: Boolean) {
        val taskClicked = viewModel.allTasks.value?.get(position)

        taskClicked?.isFavourite = !isFavourite
    }


}