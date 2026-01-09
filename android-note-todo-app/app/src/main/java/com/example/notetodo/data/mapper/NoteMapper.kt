package com.example.notetodo.data.mapper

import com.example.notetodo.data.local.entity.NoteEntity
import com.example.notetodo.domain.model.Note

/**
 * 笔记实体与领域模型转换
 */
fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        category = category,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        category = category,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
