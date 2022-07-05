package com.example.domore.app

import android.app.Application
import com.example.domore.data.TaskDatabase

class doMoreApp : Application() {
    val noteDatabase: TaskDatabase by lazy { TaskDatabase.getDatabase(this) }
}