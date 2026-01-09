package com.example.notetodo.ui.todo.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notetodo.data.local.entity.Priority
import com.example.notetodo.domain.model.Todo
import com.example.notetodo.ui.theme.PriorityHigh
import com.example.notetodo.ui.theme.PriorityLow
import com.example.notetodo.ui.theme.PriorityMedium
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onTodoClick: (Long) -> Unit,
    onAddClick: () -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val todos by viewModel.todos.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filterType by viewModel.filterType.collectAsState()
    
    var showFilterMenu by remember { mutableStateOf(false) }
    var showDeleteCompletedDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("待办") },
                actions = {
                    IconButton(onClick = { showFilterMenu = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "筛选")
                    }
                    IconButton(onClick = { showDeleteCompletedDialog = true }) {
                        Icon(Icons.Default.DeleteSweep, contentDescription = "清除已完成")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "新建待办")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 搜索框
            SearchBar(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                onClearClick = viewModel::clearSearch,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            
            // 筛选提示
            if (filterType != FilterType.ALL) {
                FilterChip(
                    selected = true,
                    onClick = { viewModel.onFilterTypeChange(FilterType.ALL) },
                    label = { 
                        Text(when(filterType) {
                            FilterType.COMPLETED -> "已完成"
                            FilterType.INCOMPLETE -> "未完成"
                            else -> "全部"
                        })
                    },
                    trailingIcon = {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "清除筛选",
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            
            // 待办列表
            if (todos.isEmpty()) {
                EmptyState(
                    message = if (searchQuery.isNotBlank()) "未找到匹配的待办" else "还没有待办事项，点击 + 创建第一条待办",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(todos, key = { it.id }) { todo ->
                        TodoItem(
                            todo = todo,
                            onClick = { onTodoClick(todo.id) },
                            onCheckedChange = { viewModel.toggleTodoComplete(todo.id, it) },
                            onDeleteClick = { viewModel.deleteTodo(todo) }
                        )
                    }
                }
            }
        }
        
        // 筛选菜单
        DropdownMenu(
            expanded = showFilterMenu,
            onDismissRequest = { showFilterMenu = false }
        ) {
            DropdownMenuItem(
                text = { Text("全部") },
                onClick = {
                    viewModel.onFilterTypeChange(FilterType.ALL)
                    showFilterMenu = false
                }
            )
            DropdownMenuItem(
                text = { Text("未完成") },
                onClick = {
                    viewModel.onFilterTypeChange(FilterType.INCOMPLETE)
                    showFilterMenu = false
                }
            )
            DropdownMenuItem(
                text = { Text("已完成") },
                onClick = {
                    viewModel.onFilterTypeChange(FilterType.COMPLETED)
                    showFilterMenu = false
                }
            )
        }
        
        // 删除已完成对话框
        if (showDeleteCompletedDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteCompletedDialog = false },
                title = { Text("清除已完成") },
                text = { Text("确定要删除所有已完成的待办事项吗？") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteCompletedTodos()
                            showDeleteCompletedDialog = false
                        }
                    ) {
                        Text("删除")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteCompletedDialog = false }) {
                        Text("取消")
                    }
                }
            )
        }
    }
}

@Composable
private fun TodoItem(
    todo: Todo,
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val isOverdue = todo.dueDate?.let { it < System.currentTimeMillis() && !todo.isCompleted } ?: false
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = if (isOverdue) {
            CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        } else {
            CardDefaults.cardColors()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = onCheckedChange
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 优先级指示器
                    PriorityIndicator(priority = todo.priority)
                    
                    Text(
                        text = todo.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                if (todo.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = todo.description,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null
                    )
                }
                
                if (todo.dueDate != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = if (isOverdue) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = formatDate(todo.dueDate),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isOverdue) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (isOverdue) {
                            Text(
                                text = "已过期",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
            
            IconButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "删除",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
    
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("删除待办") },
            text = { Text("确定要删除这条待办事项吗？") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick()
                        showDeleteDialog = false
                    }
                ) {
                    Text("删除")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
private fun PriorityIndicator(priority: Priority) {
    val color = when (priority) {
        Priority.HIGH -> PriorityHigh
        Priority.MEDIUM -> PriorityMedium
        Priority.LOW -> PriorityLow
    }
    
    Surface(
        modifier = Modifier.size(8.dp),
        shape = MaterialTheme.shapes.small,
        color = color
    ) {}
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text("搜索待办...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (query.isNotBlank()) {
                IconButton(onClick = onClearClick) {
                    Icon(Icons.Default.Close, contentDescription = "清除")
                }
            }
        },
        singleLine = true
    )
}

@Composable
private fun EmptyState(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
