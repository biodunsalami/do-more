package com.example.domore.app

import android.app.Application
import com.example.domore.data.TaskDatabase

class DoMoreApp : Application() {
    val taskDatabase: TaskDatabase by lazy { TaskDatabase.getDatabase(this) }
}