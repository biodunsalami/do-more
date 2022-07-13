package com.example.domore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domore.data.Task
import com.example.domore.databinding.ItemRecyclerListViewBinding

class TaskListAdapter(private val onItemClicked: (Task) -> Unit) : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(
    DiffCallback){

    class TaskViewHolder(private var binding: ItemRecyclerListViewBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(task: Task){
                binding.taskTitle.text = task.title
            }
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemRecyclerListViewBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }



    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }



    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldTask: Task, newTask: Task): Boolean {
                return oldTask === newTask
            }

            override fun areContentsTheSame(oldTask: Task, newTask: Task): Boolean {
                return oldTask.title == newTask.title
            }
        }
    }


}