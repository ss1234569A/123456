package com.example.notetodo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 待办事项数据库实体
 */
@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    /** 待办标题 */
    val title: String,
    
    /** 待办描述 */
    val description: String = "",
    
    /** 是否已完成 */
    val isCompleted: Boolean = false,
    
    /** 优先级 */
    val priority: Priority = Priority.MEDIUM,
    
    /** 截止日期（时间戳，可为空） */
    val dueDate: Long? = null,
    
    /** 创建时间（时间戳） */
    val createdAt: Long,
    
    /** 最后修改时间（时间戳） */
    val updatedAt: Long
)

/**
 * 优先级枚举
 */
enum class Priority {
    LOW,    // 低优先级
    MEDIUM, // 中优先级
    HIGH    // 高优先级
}
