package com.example.notetodo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notetodo.data.local.dao.NoteDao
import com.example.notetodo.data.local.dao.TodoDao
import com.example.notetodo.data.local.entity.NoteEntity
import com.example.notetodo.data.local.entity.TodoEntity

/**
 * 应用数据库
 */
@Database(
    entities = [NoteEntity::class, TodoEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun noteDao(): NoteDao
    
    abstract fun todoDao(): TodoDao
    
    companion object {
        const val DATABASE_NAME = "note_todo_database"
    }
}
