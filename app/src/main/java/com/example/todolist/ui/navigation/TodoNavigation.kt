package com.example.todolist.ui.navigation

import androidx.compose.runtime.Composable


import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.TodoApplication
import com.example.todolist.ui.screens.TodoDetailScreen
import com.example.todolist.ui.screens.TodoListScreen
import com.example.todolist.ui.viewmodel.TodoDetailViewModel
import com.example.todolist.ui.viewmodel.TodoListViewModel

@Composable
fun TodoNavigation(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val application = context.applicationContext as TodoApplication

    NavHost(
        navController = navController,
        startDestination = NavRoutes.TODO_LIST
    ) {

        composable(route = NavRoutes.TODO_LIST) {

            val todoListViewModel: TodoListViewModel = viewModel(
                factory = TodoListViewModel.Factory(application.todoRepository)
            )

            TodoListScreen(
                onTodoClick = { todo ->

                    navController.navigate(NavRoutes.todoDetailRoute(todo.id))
                },
                todoListViewModel = todoListViewModel
            )
        }


        composable(
            route = NavRoutes.TODO_DETAIL,
            arguments = listOf(
                navArgument("todoId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val todoId = backStackEntry.arguments?.getInt("todoId") ?: -1


            val todoDetailViewModel: TodoDetailViewModel = viewModel(
                factory = TodoDetailViewModel.Factory(
                    repository = application.todoRepository,
                    todoId = todoId
                )
            )

            TodoDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                todoDetailViewModel = todoDetailViewModel
            )
        }
    }
}