package com.example.domore.viewModel

import androidx.lifecycle.*
import com.example.domore.MainActivity
import com.example.domore.data.Task
import com.example.domore.data.TaskDao
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao): ViewModel() {

    val allTasks: LiveData<List<Task>> = taskDao.getTaskList().asLiveData()


    fun retrieveTask(id: Int): LiveData<Task> {
        return taskDao.getTask(id).asLiveData()
    }

    //Launching a new coroutine to insert an item in a non-blocking way
    private fun insertTask(task: Task) {
        viewModelScope.launch {
            taskDao.insert(task)
        }
    }

    private fun updateTask(task: Task){
        viewModelScope.launch {
            taskDao.update(task)
        }
    }


    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
        }
    }






    //Inserts the new Item into database.
    fun addNewTask(title: String, description: String, dueDate: String,
                   remainderTime: String, note: String, priority: Int,
                   label: String) {
        val newTask = getNewTaskEntry(title, description, dueDate, remainderTime, note, priority, label)
        insertTask(newTask)
    }


    fun updateTask(id: Int, title: String, description: String, dueDate: String,
                   remainderTime: String, note: String, priority: Int,
                   label: String){
        val updatedTask = getUpdatedTaskEntry(id, title, description, dueDate, remainderTime, note, priority, label)

        updateTask(updatedTask)
    }


    fun isEntryValid(title: String, description: String, priority: String): Boolean{
        if (title.isBlank() || description.isBlank() || priority.isBlank()){
            return false
        }
        return true
    }


    private fun getNewTaskEntry(title: String,
                                description: String,
                                dueDate: String,
                                remainderTime: String,
                                note: String,
                                priority: Int,
                                label: String): Task {
        return Task(
            title = title,
            description = description,
            dueDate = dueDate,
            remainderTime = remainderTime,
            note = note,
            priority = priority,
            label = label
        )
    }


    private fun getUpdatedTaskEntry(id: Int,
                                    title: String,
                                    description: String,
                                    dueDate: String,
                                    remainderTime: String,
                                    note: String,
                                    priority: Int,
                                    label: String): Task {
        return Task(
            id = id,
            title = title,
            description = description,
            dueDate = dueDate,
            remainderTime = remainderTime,
            note = note,
            priority = priority,
            label = label
        )
    }

}


class TaskViewModelFactory(private val taskDao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



//class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return InventoryViewModel(itemDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}