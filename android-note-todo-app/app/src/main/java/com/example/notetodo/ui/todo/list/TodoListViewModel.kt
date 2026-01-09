package com.example.notetodo.ui.todo.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetodo.data.local.entity.Priority
import com.example.notetodo.domain.model.Todo
import com.example.notetodo.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 待办列表ViewModel
 */
@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _filterType = MutableStateFlow(FilterType.ALL)
    val filterType: StateFlow<FilterType> = _filterType.asStateFlow()
    
    val todos: StateFlow<List<Todo>> = combine(
        _searchQuery,
        _filterType
    ) { query, filter ->
        Pair(query, filter)
    }.flatMapLatest { (query, filter) ->
        when {
            query.isNotBlank() -> todoRepository.searchTodos(query)
            filter == FilterType.COMPLETED -> todoRepository.getCompletedTodos()
            filter == FilterType.INCOMPLETE -> todoRepository.getIncompleteTodos()
            else -> todoRepository.getAllTodos()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    val overdueTodos: StateFlow<List<Todo>> = todoRepository.getOverdueTodos(System.currentTimeMillis())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
    
    fun onFilterTypeChange(filterType: FilterType) {
        _filterType.value = filterType
    }
    
    fun toggleTodoComplete(todoId: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            todoRepository.toggleTodoComplete(todoId, isCompleted)
        }
    }
    
    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.deleteTodo(todo)
        }
    }
    
    fun deleteCompletedTodos() {
        viewModelScope.launch {
            todoRepository.deleteCompletedTodos()
        }
    }
    
    fun clearSearch() {
        _searchQuery.value = ""
    }
}

enum class FilterType {
    ALL,
    INCOMPLETE,
    COMPLETED
}
