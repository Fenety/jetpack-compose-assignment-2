package com.example.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.model.Todo
import com.example.todolist.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TodoListViewModel(private val repository: TodoRepository) : ViewModel() {


    private val _uiState = MutableStateFlow<TodoListUiState>(TodoListUiState.Loading)
    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()

    init {
        loadTodos()
    }


    fun loadTodos() {
        viewModelScope.launch {
            _uiState.value = TodoListUiState.Loading


            try {
                repository.refreshTodos()
            } catch (e: Exception) {

            }


            repository.getAllTodos()
                .catch { e ->
                    _uiState.value = TodoListUiState.Error(e.message ?: "Unknown error")
                }
                .collect { todos ->
                    _uiState.value = if (todos.isEmpty()) {
                        TodoListUiState.Empty
                    } else {
                        TodoListUiState.Success(todos)
                    }
                }
        }
    }


    class Factory(private val repository: TodoRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TodoListViewModel::class.java)) {
                return TodoListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


sealed class TodoListUiState {
    data object Loading : TodoListUiState()
    data class Success(val todos: List<Todo>) : TodoListUiState()
    data object Empty : TodoListUiState()
    data class Error(val message: String) : TodoListUiState()
}