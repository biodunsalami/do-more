package com.example.domore.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domore.R
import com.example.domore.adapter.TaskInfoInterface
import com.example.domore.adapter.TaskListAdapter
import com.example.domore.data.Task
import com.example.domore.databinding.FragmentFavouritesBinding


class FavouritesFragment : SharedFragment(), TaskInfoInterface {

    private lateinit var binding: FragmentFavouritesBinding

    private lateinit var taskAdapter: TaskListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favouriteTaskLabel.text = getString(R.string.task_type_header, "Favourite")

        binding.fab.setOnClickListener {
            activityCast().binding.bottomSheet.visibility = View.VISIBLE
        }

        taskAdapter = TaskListAdapter(this@FavouritesFragment, requireContext())


        binding.listRecyclerView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.allTasks.observe(this.viewLifecycleOwner){tasks ->
            tasks.let {
                var list = mutableListOf<Task>()
//                it.filter { task -> task.isFavourite }
//                list = it as MutableList<Task>
                taskAdapter.submitList(it.filter { task -> task.isFavourite })

                if(it.isNotEmpty()){
                    binding.noFavouriteTaskLabel.visibility = View.GONE
                    binding.noTaskBg.visibility = View.GONE
                }
            }
        }


    }

    fun filterList(list: MutableList<Task>): MutableList<Task> {
        list.filter { task -> task.isFavourite }
        return list
    }

    override fun onCardClicked(position: Int) {
        val taskClicked = viewModel.allTasks.value?.get(position)

        val action = taskClicked?.id?.let {
            FavouritesFragmentDirections.actionFavouritesFragmentToTaskDetailsFragment(
                it
            )
        }
        if (action != null) {
            this.findNavController().navigate(action)
        }
        taskAdapter.notifyDataSetChanged()
    }

    override fun onFavouriteClicked(position: Int, isFavourite: Boolean) {
        val taskClicked = viewModel.allTasks.value?.get(position)
        taskClicked?.isFavourite = isFavourite
        Log.e("Position", "$position")

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
