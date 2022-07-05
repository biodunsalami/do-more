package com.example.domore.viewModel

import androidx.lifecycle.*
import com.example.domore.data.Task
import com.example.domore.data.TaskDao
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao): ViewModel() {

    val allTasks: LiveData<List<Task>> = taskDao.getTaskList().asLiveData()

//    val sortedTasks = mutableListOf<Task>()
//
//    fun sortTask(){
//
//    }

    fun retrieveTask(id: Int): LiveData<Task> {
        return taskDao.getTask(id).asLiveData()
    }

    //Launching a new coroutine to insert an item in a non-blocking way
    private fun insertTask(task: Task) {
        viewModelScope.launch {
            taskDao.insert(task)
        }
    }

    fun updateTask(task: Task){
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
    fun addNewItem(title: String, description: String, dueDate: String,
                   remainderTime: String, note: String, priority: Int,
                   label: String, isDone: Boolean) {
        val newItem = getNewTaskEntry(title, description, dueDate, remainderTime, note, priority, label, isDone)
        insertTask(newItem)
    }


    fun updateTask(id: Int, title: String, description: String, dueDate: String,
                   remainderTime: String, note: String, priority: Int,
                   label: String, isDone: Boolean){
        val updatedTask = getUpdatedTaskEntry(id, title, description, dueDate, remainderTime, note, priority, label, isDone)

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
                                label: String,
                                isDone: Boolean): Task {
        return Task(
            title = title,
            description = description,
            dueDate = dueDate,
            remainderTime = remainderTime,
            note = note,
            priority = priority,
            label = label,
            isDone = isDone
        )
    }


    private fun getUpdatedTaskEntry(id: Int,
                                title: String,
                                description: String,
                                dueDate: String,
                                remainderTime: String,
                                note: String,
                                priority: Int,
                                label: String,
                                isDone: Boolean): Task {
        return Task(
            id = id,
            title = title,
            description = description,
            dueDate = dueDate,
            remainderTime = remainderTime,
            note = note,
            priority = priority,
            label = label,
            isDone = isDone
        )
    }

}


//class TaskViewModelFactory(private val taskDao: TaskDao) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return TaskViewModel(taskDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}



//class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return InventoryViewModel(itemDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}