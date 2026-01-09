package com.example.notetodo.data.local.database

import androidx.room.TypeConverter
import com.example.notetodo.data.local.entity.Priority

/**
 * Room类型转换器
 */
class Converters {
    
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }
    
    @TypeConverter
    fun toPriority(value: String): Priority {
        return Priority.valueOf(value)
    }
}
