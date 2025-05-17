package com.example.todolist.data.repository

import com.example.todolist.data.local.TodoDao
import com.example.todolist.data.model.Todo
import com.example.todolist.data.remote.TodoApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TodoRepository(
    private val todoApiService: TodoApiService,
    private val todoDao: TodoDao
) {

    fun getAllTodos(): Flow<List<Todo>> = todoDao.getAllTodos()


    fun getTodoById(id: Int): Flow<Todo> = todoDao.getTodoById(id)


    suspend fun refreshTodos() {
        withContext(Dispatchers.IO) {
            try {
                val remoteTodos = todoApiService.getTodos()
                todoDao.insertTodos(remoteTodos)
            } catch (e: Exception) {

            }
        }
    }


    suspend fun refreshTodoById(id: Int) {
        withContext(Dispatchers.IO) {
            try {
                val remoteTodo = todoApiService.getTodoById(id)
                todoDao.insertTodos(listOf(remoteTodo))
            } catch (e: Exception) {

            }
        }
    }
}