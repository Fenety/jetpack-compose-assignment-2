package com.example.todolist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.data.model.Todo
import com.example.todolist.ui.theme.TodoappTheme

@Composable
fun TodoItem(
    todo: Todo,
    onTodoClick: (Todo) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTodoClick(todo) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = if (todo.completed) Icons.Default.Check else Icons.Default.Close,
                contentDescription = if (todo.completed) "Completed" else "Pending",
                tint = if (todo.completed) Color(0xFF4CAF50) else Color(0xFFF44336), // Green or Red
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.End)
            )

            Text(
                text = todo.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )


            Text(
                text = "User #${todo.userId}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    TodoappTheme {
        TodoItem(
            todo = Todo(
                id = 1,
                userId = 1,
                title = "This is a sample todo item for preview",
                completed = false
            ),
            onTodoClick = {}
        )
    }
}
