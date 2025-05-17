package com.example.todolist.ui.navigation

object NavRoutes {
    const val TODO_LIST = "todo_list"
    const val TODO_DETAIL = "todo_detail/{todoId}"


    fun todoDetailRoute(todoId: Int) = "todo_detail/$todoId"
}