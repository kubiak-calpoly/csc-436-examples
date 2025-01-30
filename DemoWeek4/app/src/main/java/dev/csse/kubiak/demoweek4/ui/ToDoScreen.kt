package dev.csse.kubiak.demoweek4.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.kubiak.demoweek4.Task
import dev.csse.kubiak.demoweek4.ui.theme.DemoWeek4Theme
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreen(
  modifier: Modifier = Modifier,
  todoViewModel: ToDoViewModel = viewModel()
) {
  var showConfirmationDialog by remember { mutableStateOf(false) }
  var showTaskInput by remember { mutableStateOf(false) }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Todo List") },
        colors = TopAppBarColors(
          containerColor = MaterialTheme.colorScheme.primary,
          scrolledContainerColor = MaterialTheme.colorScheme.primary,
          navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
          titleContentColor = MaterialTheme.colorScheme.onPrimary,
          actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
          IconButton(
            onClick = {
              showConfirmationDialog = true
            },
            enabled = todoViewModel.completedTasksExist
          ) {
            Icon(
              imageVector = Icons.Default.Delete,
              contentDescription = "Delete completed tasks"
            )
          }
        }

      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = { showTaskInput = true }
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "Add a task"
        )
      }
    }
  ) { innerPadding ->
    TaskList(
      showTaskInput = showTaskInput,
      onDismissInput = { showTaskInput = false },
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
    )
  }

  if (showConfirmationDialog) {
    DeleteConfirmationDialog(
      onConfirm = {
        todoViewModel.deleteCompletedTasks()
        showConfirmationDialog = false
      },
      onDismiss = {
        showConfirmationDialog = false
      }
    )
  }
}

@Composable
fun DeleteConfirmationDialog(
  onConfirm: () -> Unit,
  onDismiss: () -> Unit
) {
  AlertDialog(
    text = { Text("Ok to delete completed tasks?") },
    onDismissRequest = {
      onDismiss()
    },
    confirmButton = {
      TextButton(
        onClick = { onConfirm() }
      ) { Text("Confirm") }
    },
    dismissButton = {
      TextButton(
        onClick = { onDismiss() }
      ) { Text("Dismiss") }
    }
  ) // no body
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TaskList(
  modifier: Modifier = Modifier,
  showTaskInput: Boolean = true,
  onDismissInput: () -> Unit,
  todoViewModel: ToDoViewModel = viewModel()
) {
  val focusRequester = remember { FocusRequester() }

  LazyColumn(modifier = modifier) {
    stickyHeader {
      LaunchedEffect(showTaskInput) {
        if (showTaskInput) {
          focusRequester.requestFocus()
        }
      }
      AnimatedVisibility(showTaskInput) {
        AddTaskInput(
          modifier = Modifier.focusRequester(focusRequester),
          onDismiss = onDismissInput
        )
        { s ->
          todoViewModel.addTask(s)
          onDismissInput()
        }
      }
    }
    items(
      items = todoViewModel.taskList,
      key = { t -> t.id }
    ) { task ->
      TaskCard(
        task = task,
        toggleCompleted = todoViewModel::toggleTaskCompleted
      )
    }
  }
}


@Composable
fun AddTaskInput(
  modifier: Modifier = Modifier,
  onDismiss: () -> Unit,
  onEnterTask: (Task) -> Unit,
) {
  val keyboardController = LocalSoftwareKeyboardController.current
  var taskBody by remember { mutableStateOf("") }

  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    OutlinedTextField(
      modifier = modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(6.dp),
      value = taskBody,
      onValueChange = { taskBody = it },
      label = { Text("Enter task") },
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
      keyboardActions = KeyboardActions(
        onDone = {
          keyboardController?.hide()
          onEnterTask(Task(body = taskBody))
        }
      )
    )
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp) ) {
      Button(
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.secondary
        ),
        onClick = {
          onDismiss()
        }) {
        Text("Cancel")
      }
      Button(
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.primary
        ),
        onClick = {
          onEnterTask(Task(body = taskBody))
        }
      ) {
        Text("Add Task")
      }
    }
  }
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