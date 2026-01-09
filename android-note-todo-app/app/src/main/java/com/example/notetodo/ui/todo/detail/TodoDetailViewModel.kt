package com.example.notetodo.ui.todo.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetodo.data.local.entity.Priority
import com.example.notetodo.domain.model.Todo
import com.example.notetodo.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 待办详情ViewModel
 */
@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val todoId: Long? = savedStateHandle.get<Long>("todoId")
    
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()
    
    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()
    
    private val _priority = MutableStateFlow(Priority.MEDIUM)
    val priority: StateFlow<Priority> = _priority.asStateFlow()
    
    private val _dueDate = MutableStateFlow<Long?>(null)
    val dueDate: StateFlow<Long?> = _dueDate.asStateFlow()
    
    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()
    
    init {
        todoId?.let { loadTodo(it) }
    }
    
    private fun loadTodo(id: Long) {
        viewModelScope.launch {
            todoRepository.getTodoById(id)?.let { todo ->
                _title.value = todo.title
                _description.value = todo.description
                _priority.value = todo.priority
                _dueDate.value = todo.dueDate
            }
        }
    }
    
    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
    }
    
    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }
    
    fun onPriorityChange(newPriority: Priority) {
        _priority.value = newPriority
    }
    
    fun onDueDateChange(newDueDate: Long?) {
        _dueDate.value = newDueDate
    }
    
    fun saveTodo(onSuccess: () -> Unit) {
        if (_title.value.isBlank()) {
            return
        }
        
        viewModelScope.launch {
            _isSaving.value = true
            try {
                val currentTime = System.currentTimeMillis()
                val todo = Todo(
                    id = todoId ?: 0,
                    title = _title.value.trim(),
                    description = _description.value.trim(),
                    isCompleted = false,
                    priority = _priority.value,
                    dueDate = _dueDate.value,
                    createdAt = todoId?.let { currentTime } ?: currentTime,
                    updatedAt = currentTime
                )
                
                if (todoId == null) {
                    todoRepository.insertTodo(todo)
                } else {
                    todoRepository.updateTodo(todo)
                }
                
                onSuccess()
            } finally {
                _isSaving.value = false
            }
        }
    }
}
