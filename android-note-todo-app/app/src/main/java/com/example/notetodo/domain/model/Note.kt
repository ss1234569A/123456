package com.example.notetodo.domain.model

/**
 * 笔记领域模型
 */
data class Note(
    val id: Long = 0,
    val title: String,
    val content: String,
    val category: String = "",
    val createdAt: Long,
    val updatedAt: Long
)
