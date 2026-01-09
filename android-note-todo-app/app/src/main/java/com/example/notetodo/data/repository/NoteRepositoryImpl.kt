package com.example.notetodo.data.repository

import com.example.notetodo.data.local.dao.NoteDao
import com.example.notetodo.data.mapper.toEntity
import com.example.notetodo.data.mapper.toNote
import com.example.notetodo.domain.model.Note
import com.example.notetodo.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 笔记仓库实现
 */
class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {
    
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { entities ->
            entities.map { it.toNote() }
        }
    }
    
    override suspend fun getNoteById(noteId: Long): Note? {
        return noteDao.getNoteById(noteId)?.toNote()
    }
    
    override fun searchNotes(query: String): Flow<List<Note>> {
        return noteDao.searchNotes(query).map { entities ->
            entities.map { it.toNote() }
        }
    }
    
    override fun getNotesByCategory(category: String): Flow<List<Note>> {
        return noteDao.getNotesByCategory(category).map { entities ->
            entities.map { it.toNote() }
        }
    }
    
    override fun getAllCategories(): Flow<List<String>> {
        return noteDao.getAllCategories()
    }
    
    override suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note.toEntity())
    }
    
    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toEntity())
    }
    
    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toEntity())
    }
    
    override suspend fun deleteNoteById(noteId: Long) {
        noteDao.deleteNoteById(noteId)
    }
    
    override suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }
}
