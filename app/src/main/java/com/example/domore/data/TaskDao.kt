package com.example.domore.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task" )
    fun getTaskList(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id = :id")
    fun getTask(id: Int): Flow<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

}