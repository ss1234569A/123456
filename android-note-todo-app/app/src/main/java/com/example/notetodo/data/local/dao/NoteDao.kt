package com.example.notetodo.data.local.dao

import androidx.room.*
import com.example.notetodo.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

/**
 * 笔记数据访问对象
 */
@Dao
interface NoteDao {
    
    /**
     * 获取所有笔记（按更新时间倒序）
     */
    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>
    
    /**
     * 根据ID获取笔记
     */
    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Long): NoteEntity?
    
    /**
     * 搜索笔记（标题或内容包含关键词）
     */
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY updatedAt DESC")
    fun searchNotes(query: String): Flow<List<NoteEntity>>
    
    /**
     * 根据分类获取笔记
     */
    @Query("SELECT * FROM notes WHERE category = :category ORDER BY updatedAt DESC")
    fun getNotesByCategory(category: String): Flow<List<NoteEntity>>
    
    /**
     * 获取所有分类
     */
    @Query("SELECT DISTINCT category FROM notes WHERE category != '' ORDER BY category")
    fun getAllCategories(): Flow<List<String>>
    
    /**
     * 插入笔记
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long
    
    /**
     * 更新笔记
     */
    @Update
    suspend fun updateNote(note: NoteEntity)
    
    /**
     * 删除笔记
     */
    @Delete
    suspend fun deleteNote(note: NoteEntity)
    
    /**
     * 根据ID删除笔记
     */
    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Long)
    
    /**
     * 删除所有笔记
     */
    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}
