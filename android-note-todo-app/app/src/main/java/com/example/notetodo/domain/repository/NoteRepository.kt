package com.example.notetodo.domain.repository

import com.example.notetodo.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * 笔记仓库接口
 */
interface NoteRepository {
    
    fun getAllNotes(): Flow<List<Note>>
    
    suspend fun getNoteById(noteId: Long): Note?
    
    fun searchNotes(query: String): Flow<List<Note>>
    
    fun getNotesByCategory(category: String): Flow<List<Note>>
    
    fun getAllCategories(): Flow<List<String>>
    
    suspend fun insertNote(note: Note): Long
    
    suspend fun updateNote(note: Note)
    
    suspend fun deleteNote(note: Note)
    
    suspend fun deleteNoteById(noteId: Long)
    
    suspend fun deleteAllNotes()
}
