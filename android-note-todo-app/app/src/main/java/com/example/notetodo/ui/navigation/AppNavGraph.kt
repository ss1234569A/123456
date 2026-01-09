package com.example.notetodo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notetodo.ui.note.detail.NoteDetailScreen
import com.example.notetodo.ui.note.list.NoteListScreen
import com.example.notetodo.ui.todo.detail.TodoDetailScreen
import com.example.notetodo.ui.todo.list.TodoListScreen

/**
 * 应用导航图
 */
@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 笔记列表
        composable(Screen.NoteList.route) {
            NoteListScreen(
                onNoteClick = { noteId ->
                    navController.navigate(Screen.NoteDetail.createRoute(noteId))
                },
                onAddClick = {
                    navController.navigate(Screen.NoteDetail.createRoute())
                }
            )
        }
        
        // 笔记详情
        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )
        ) {
            NoteDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // 待办列表
        composable(Screen.TodoList.route) {
            TodoListScreen(
                onTodoClick = { todoId ->
                    navController.navigate(Screen.TodoDetail.createRoute(todoId))
                },
                onAddClick = {
                    navController.navigate(Screen.TodoDetail.createRoute())
                }
            )
        }
        
        // 待办详情
        composable(
            route = Screen.TodoDetail.route,
            arguments = listOf(
                navArgument("todoId") {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )
        ) {
            TodoDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
