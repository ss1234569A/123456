package com.example.notetodo.domain.model

import com.example.notetodo.data.local.entity.Priority

/**
 * 待办事项领域模型
 */
data class Todo(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.MEDIUM,
    val dueDate: Long? = null,
    val createdAt: Long,
    val updatedAt: Long
)
