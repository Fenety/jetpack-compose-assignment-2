package com.example.todolist

import android.app.Application
import com.example.todolist.data.local.TodoDatabase
import com.example.todolist.di.NetworkModule
import com.example.todolist.data.repository.TodoRepository

class TodoApplication : Application() {
    // Repositories and services that will be used throughout the app
    val database by lazy { TodoDatabase.getDatabase(this) }
    val todoApiService by lazy { NetworkModule.provideTodoApiService() }
    val todoRepository by lazy { TodoRepository(todoApiService, database.todoDao()) }
}