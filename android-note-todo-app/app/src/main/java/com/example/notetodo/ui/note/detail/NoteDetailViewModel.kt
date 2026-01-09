package com.example.notetodo.ui.note.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetodo.domain.model.Note
import com.example.notetodo.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 笔记详情ViewModel
 */
@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val noteId: Long? = savedStateHandle.get<Long>("noteId")
    
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()
    
    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()
    
    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category.asStateFlow()
    
    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()
    
    init {
        noteId?.let { loadNote(it) }
    }
    
    private fun loadNote(id: Long) {
        viewModelScope.launch {
            noteRepository.getNoteById(id)?.let { note ->
                _title.value = note.title
                _content.value = note.content
                _category.value = note.category
            }
        }
    }
    
    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
    }
    
    fun onContentChange(newContent: String) {
        _content.value = newContent
    }
    
    fun onCategoryChange(newCategory: String) {
        _category.value = newCategory
    }
    
    fun saveNote(onSuccess: () -> Unit) {
        if (_title.value.isBlank()) {
            return
        }
        
        viewModelScope.launch {
            _isSaving.value = true
            try {
                val currentTime = System.currentTimeMillis()
                val note = Note(
                    id = noteId ?: 0,
                    title = _title.value.trim(),
                    content = _content.value.trim(),
                    category = _category.value.trim(),
                    createdAt = noteId?.let { currentTime } ?: currentTime,
                    updatedAt = currentTime
                )
                
                if (noteId == null) {
                    noteRepository.insertNote(note)
                } else {
                    noteRepository.updateNote(note)
                }
                
                onSuccess()
            } finally {
                _isSaving.value = false
            }
        }
    }
}
