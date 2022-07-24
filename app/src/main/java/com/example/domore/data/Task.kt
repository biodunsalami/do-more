package com.example.domore.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val dueDate: String,
    val remainderTime: String = "",
    val note: String = "",
    val priority: Int,
    val label: String,
    var isFavourite: Boolean = false,
    var isDone: Boolean = false
)
