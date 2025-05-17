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

class TodoDetailViewModel(
    private val repository: TodoRepository,
    private val todoId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow<TodoDetailUiState>(TodoDetailUiState.Loading)
    val uiState: StateFlow<TodoDetailUiState> = _uiState.asStateFlow()

    init {
        loadTodoDetail()
    }


    fun loadTodoDetail() {
        viewModelScope.launch {
            _uiState.value = TodoDetailUiState.Loading


            try {
                repository.refreshTodoById(todoId)
            } catch (e: Exception) {

            }


            repository.getTodoById(todoId)
                .catch { e ->
                    _uiState.value = TodoDetailUiState.Error(e.message ?: "Unknown error")
                }
                .collect { todo ->
                    _uiState.value = TodoDetailUiState.Success(todo)
                }
        }
    }


    class Factory(
        private val repository: TodoRepository,
        private val todoId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TodoDetailViewModel::class.java)) {
                return TodoDetailViewModel(repository, todoId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


sealed class TodoDetailUiState {
    data object Loading : TodoDetailUiState()
    data class Success(val todo: Todo) : TodoDetailUiState()
    data class Error(val message: String) : TodoDetailUiState()
}