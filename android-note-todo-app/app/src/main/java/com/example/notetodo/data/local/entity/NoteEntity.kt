package com.example.notetodo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 笔记数据库实体
 */
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    /** 笔记标题 */
    val title: String,
    
    /** 笔记内容 */
    val content: String,
    
    /** 分类/标签 */
    val category: String = "",
    
    /** 创建时间（时间戳） */
    val createdAt: Long,
    
    /** 最后修改时间（时间戳） */
    val updatedAt: Long
)
