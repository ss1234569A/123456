package com.example.notetodo.data.local.dao

import androidx.room.*
import com.example.notetodo.data.local.entity.Priority
import com.example.notetodo.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

/**
 * 待办事项数据访问对象
 */
@Dao
interface TodoDao {
    
    /**
     * 获取所有待办（按优先级和创建时间排序）
     */
    @Query("SELECT * FROM todos ORDER BY CASE priority WHEN 'HIGH' THEN 1 WHEN 'MEDIUM' THEN 2 WHEN 'LOW' THEN 3 END, createdAt DESC")
    fun getAllTodos(): Flow<List<TodoEntity>>
    
    /**
     * 根据ID获取待办
     */
    @Query("SELECT * FROM todos WHERE id = :todoId")
    suspend fun getTodoById(todoId: Long): TodoEntity?
    
    /**
     * 获取未完成的待办
     */
    @Query("SELECT * FROM todos WHERE isCompleted = 0 ORDER BY CASE priority WHEN 'HIGH' THEN 1 WHEN 'MEDIUM' THEN 2 WHEN 'LOW' THEN 3 END, createdAt DESC")
    fun getIncompleteTodos(): Flow<List<TodoEntity>>
    
    /**
     * 获取已完成的待办
     */
    @Query("SELECT * FROM todos WHERE isCompleted = 1 ORDER BY updatedAt DESC")
    fun getCompletedTodos(): Flow<List<TodoEntity>>
    
    /**
     * 搜索待办（标题或描述包含关键词）
     */
    @Query("SELECT * FROM todos WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY CASE priority WHEN 'HIGH' THEN 1 WHEN 'MEDIUM' THEN 2 WHEN 'LOW' THEN 3 END, createdAt DESC")
    fun searchTodos(query: String): Flow<List<TodoEntity>>
    
    /**
     * 根据优先级获取待办
     */
    @Query("SELECT * FROM todos WHERE priority = :priority ORDER BY createdAt DESC")
    fun getTodosByPriority(priority: Priority): Flow<List<TodoEntity>>
    
    /**
     * 获取过期的待办（未完成且截止日期已过）
     */
    @Query("SELECT * FROM todos WHERE isCompleted = 0 AND dueDate IS NOT NULL AND dueDate < :currentTime ORDER BY dueDate ASC")
    fun getOverdueTodos(currentTime: Long): Flow<List<TodoEntity>>
    
    /**
     * 插入待办
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity): Long
    
    /**
     * 更新待办
     */
    @Update
    suspend fun updateTodo(todo: TodoEntity)
    
    /**
     * 切换待办完成状态
     */
    @Query("UPDATE todos SET isCompleted = :isCompleted, updatedAt = :updatedAt WHERE id = :todoId")
    suspend fun toggleTodoComplete(todoId: Long, isCompleted: Boolean, updatedAt: Long)
    
    /**
     * 删除待办
     */
    @Delete
    suspend fun deleteTodo(todo: TodoEntity)
    
    /**
     * 根据ID删除待办
     */
    @Query("DELETE FROM todos WHERE id = :todoId")
    suspend fun deleteTodoById(todoId: Long)
    
    /**
     * 删除所有已完成的待办
     */
    @Query("DELETE FROM todos WHERE isCompleted = 1")
    suspend fun deleteCompletedTodos()
    
    /**
     * 删除所有待办
     */
    @Query("DELETE FROM todos")
    suspend fun deleteAllTodos()
}
