package com.example.notetodo.data.repository

import com.example.notetodo.data.local.dao.TodoDao
import com.example.notetodo.data.local.entity.Priority
import com.example.notetodo.data.mapper.toEntity
import com.example.notetodo.data.mapper.toTodo
import com.example.notetodo.domain.model.Todo
import com.example.notetodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 待办事项仓库实现
 */
class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {
    
    override fun getAllTodos(): Flow<List<Todo>> {
        return todoDao.getAllTodos().map { entities ->
            entities.map { it.toTodo() }
        }
    }
    
    override suspend fun getTodoById(todoId: Long): Todo? {
        return todoDao.getTodoById(todoId)?.toTodo()
    }
    
    override fun getIncompleteTodos(): Flow<List<Todo>> {
        return todoDao.getIncompleteTodos().map { entities ->
            entities.map { it.toTodo() }
        }
    }
    
    override fun getCompletedTodos(): Flow<List<Todo>> {
        return todoDao.getCompletedTodos().map { entities ->
            entities.map { it.toTodo() }
        }
    }
    
    override fun searchTodos(query: String): Flow<List<Todo>> {
        return todoDao.searchTodos(query).map { entities ->
            entities.map { it.toTodo() }
        }
    }
    
    override fun getTodosByPriority(priority: Priority): Flow<List<Todo>> {
        return todoDao.getTodosByPriority(priority).map { entities ->
            entities.map { it.toTodo() }
        }
    }
    
    override fun getOverdueTodos(currentTime: Long): Flow<List<Todo>> {
        return todoDao.getOverdueTodos(currentTime).map { entities ->
            entities.map { it.toTodo() }
        }
    }
    
    override suspend fun insertTodo(todo: Todo): Long {
        return todoDao.insertTodo(todo.toEntity())
    }
    
    override suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo.toEntity())
    }
    
    override suspend fun toggleTodoComplete(todoId: Long, isCompleted: Boolean) {
        todoDao.toggleTodoComplete(todoId, isCompleted, System.currentTimeMillis())
    }
    
    override suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo.toEntity())
    }
    
    override suspend fun deleteTodoById(todoId: Long) {
        todoDao.deleteTodoById(todoId)
    }
    
    override suspend fun deleteCompletedTodos() {
        todoDao.deleteCompletedTodos()
    }
    
    override suspend fun deleteAllTodos() {
        todoDao.deleteAllTodos()
    }
}
