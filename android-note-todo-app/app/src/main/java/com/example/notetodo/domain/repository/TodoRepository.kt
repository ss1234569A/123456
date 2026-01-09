package com.example.notetodo.domain.repository

import com.example.notetodo.data.local.entity.Priority
import com.example.notetodo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

/**
 * 待办事项仓库接口
 */
interface TodoRepository {
    
    fun getAllTodos(): Flow<List<Todo>>
    
    suspend fun getTodoById(todoId: Long): Todo?
    
    fun getIncompleteTodos(): Flow<List<Todo>>
    
    fun getCompletedTodos(): Flow<List<Todo>>
    
    fun searchTodos(query: String): Flow<List<Todo>>
    
    fun getTodosByPriority(priority: Priority): Flow<List<Todo>>
    
    fun getOverdueTodos(currentTime: Long): Flow<List<Todo>>
    
    suspend fun insertTodo(todo: Todo): Long
    
    suspend fun updateTodo(todo: Todo)
    
    suspend fun toggleTodoComplete(todoId: Long, isCompleted: Boolean)
    
    suspend fun deleteTodo(todo: Todo)
    
    suspend fun deleteTodoById(todoId: Long)
    
    suspend fun deleteCompletedTodos()
    
    suspend fun deleteAllTodos()
}
