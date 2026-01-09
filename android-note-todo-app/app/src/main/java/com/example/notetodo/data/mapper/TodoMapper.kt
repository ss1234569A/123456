package com.example.notetodo.data.mapper

import com.example.notetodo.data.local.entity.TodoEntity
import com.example.notetodo.domain.model.Todo

/**
 * 待办实体与领域模型转换
 */
fun TodoEntity.toTodo(): Todo {
    return Todo(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        priority = priority,
        dueDate = dueDate,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Todo.toEntity(): TodoEntity {
    return TodoEntity(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        priority = priority,
        dueDate = dueDate,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
