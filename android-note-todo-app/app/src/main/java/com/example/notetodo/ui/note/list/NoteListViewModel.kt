package com.example.notetodo.ui.note.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetodo.domain.model.Note
import com.example.notetodo.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 笔记列表ViewModel
 */
@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()
    
    val notes: StateFlow<List<Note>> = combine(
        _searchQuery,
        _selectedCategory
    ) { query, category ->
        Pair(query, category)
    }.flatMapLatest { (query, category) ->
        when {
            query.isNotBlank() -> noteRepository.searchNotes(query)
            category != null -> noteRepository.getNotesByCategory(category)
            else -> noteRepository.getAllNotes()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    val categories: StateFlow<List<String>> = noteRepository.getAllCategories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
    
    fun onCategorySelected(category: String?) {
        _selectedCategory.value = category
    }
    
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }
    
    fun clearSearch() {
        _searchQuery.value = ""
    }
}
