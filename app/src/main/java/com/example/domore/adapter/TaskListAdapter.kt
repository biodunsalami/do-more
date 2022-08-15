package com.example.domore.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domore.R
import com.example.domore.data.Task
import com.example.domore.databinding.ItemRecyclerListViewBinding

class TaskListAdapter(private val taskInfoInterface: TaskInfoInterface,
                      private val context: Context) :
    ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallback){

    inner class TaskViewHolder(private var binding: ItemRecyclerListViewBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(task: Task, taskInfoInterface: TaskInfoInterface){
                binding.doneCheckbox.isChecked = task.isDone
                binding.taskTitle.text = task.title
                binding.favouriteStarCheckbox.isChecked = task.isFavourite



                binding.doneCheckbox.setOnClickListener {
                    taskInfoInterface.onDoneClicked(adapterPosition, binding.doneCheckbox.isChecked)
                }

                binding.favouriteStarCheckbox.setOnClickListener {
                    taskInfoInterface.onFavouriteClicked(adapterPosition, binding.favouriteStarCheckbox.isChecked)
                }

                binding.cardView.setOnClickListener {
                    taskInfoInterface.onCardClicked(adapterPosition)
                }

                if(binding.doneCheckbox.isChecked){
                    binding.taskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    binding.cardView.alpha = 0.5F
                    binding.favouriteStarCheckbox.isClickable = false
                }else{
                    binding.taskTitle.paintFlags = Paint.CURSOR_AT
                    binding.cardView.alpha = 1F
                    binding.favouriteStarCheckbox.isClickable = true

                }
            }
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemRecyclerListViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }



    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)

        holder.bind(current, taskInfoInterface)

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