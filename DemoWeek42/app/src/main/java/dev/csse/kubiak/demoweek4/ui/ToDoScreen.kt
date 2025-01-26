package dev.csse.kubiak.demoweek4.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.kubiak.demoweek4.Task
import dev.csse.kubiak.demoweek4.ui.theme.DemoWeek4Theme

@Composable
fun ToDoScreen(
  modifier: Modifier = Modifier
) {
  TaskList(modifier = modifier.fillMaxSize())
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TaskList(
  modifier: Modifier = Modifier,
  todoViewModel: ToDoViewModel = viewModel()
) {
  Box(modifier = Modifier.fillMaxSize()) {
    LazyColumn(modifier = modifier) {
      stickyHeader {
        AddTaskInput { s -> todoViewModel.addTask(s) }
      }
      items(
        items = todoViewModel.taskList,
        key =  { task -> task.id }
      ) { task ->
        TaskCard(
          task = task,
          toggleCompleted = { t ->
            todoViewModel.toggleTaskCompleted(t)
          }
        )
      }
    }

    Row(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(10.dp)
    ) {
      Button(
        onClick = { todoViewModel.deleteCompletedTasks() },
        enabled = todoViewModel.completedTasksExist
      ) {
        Icon(
          imageVector = Icons.Default.Delete,
          contentDescription = "Delete completed tasks"
        )
        Text("Delete Completed Tasks")
      }
    }
  }
}

@Composable
fun AddTaskInput(onEnterTask: (String) -> Unit) {
  val keyboardController = LocalSoftwareKeyboardController.current
  var taskBody by remember { mutableStateOf("") }

  OutlinedTextField(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color.White)
      .padding(6.dp),
    value = taskBody,
    onValueChange = { taskBody = it },
    label = { Text("Enter task") },
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions = KeyboardActions(
      onDone = {
        onEnterTask(taskBody)
        taskBody = ""
        keyboardController?.hide()
      }
    )
  )
}

@Composable
fun TaskCard(
  task: Task,
  toggleCompleted: (Task) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .padding(8.dp)
      .fillMaxWidth(),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant
    )
  ) {
    Row(
      modifier = modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Checkbox(
        checked = task.completed,
        onCheckedChange = {
          toggleCompleted(task)
        }
      )
      Text(
        text = task.body,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier.padding(12.dp),
        color = if (task.completed) Color.Gray else Color.Black
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ToDoScreenPreview() {
  val todoViewModel = viewModel<ToDoViewModel>()
  todoViewModel.createTestTasks(5)

  DemoWeek4Theme(dynamicColor = false) {
    ToDoScreen()
  }
}