package com.example.notetodo.ui.navigation

/**
 * 导航路由定义
 */
sealed class Screen(val route: String) {
    object NoteList : Screen("note_list")
    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Long? = null): String {
            return if (noteId != null) "note_detail/$noteId" else "note_detail/0"
        }
    }
    
    object TodoList : Screen("todo_list")
    object TodoDetail : Screen("todo_detail/{todoId}") {
        fun createRoute(todoId: Long? = null): String {
            return if (todoId != null) "todo_detail/$todoId" else "todo_detail/0"
        }
    }
}
