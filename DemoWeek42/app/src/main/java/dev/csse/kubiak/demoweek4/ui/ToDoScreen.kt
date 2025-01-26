package dev.csse.kubiak.demoweek4.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.ScaffoldSubcomposeInMeasureFix
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.kubiak.demoweek4.Task
import dev.csse.kubiak.demoweek4.ui.theme.DemoWeek4Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreen(
  modifier: Modifier = Modifier,
  todoViewModel: ToDoViewModel = viewModel()
) {
  var showDeleteTasksDialog by remember { mutableStateOf(false) }
  var showAddTaskInput by remember { mutableStateOf(false) }
  val addTaskFocusRequester = remember { FocusRequester() }

  if (showDeleteTasksDialog) {
    DeleteTasksDialog(
      onDismiss = {
        showDeleteTasksDialog = false
      },
      onConfirm = {
        showDeleteTasksDialog = false
        todoViewModel.deleteCompletedTasks()
      }
    )
  }

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
            onClick = { showDeleteTasksDialog = true },
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
        onClick = {
          showAddTaskInput = true
          addTaskFocusRequester.requestFocus()
        }
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "Add a task"
        )
      }
    }
  ) { innerPadding ->
    TaskList(
      showAddTaskInput = showAddTaskInput,
      onDismiss = {showAddTaskInput = false},
      focusRequester = addTaskFocusRequester,
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TaskList(
  modifier: Modifier = Modifier,
  showAddTaskInput: Boolean = true,
  onDismiss: () -> Unit = {},
  focusRequester: FocusRequester?,
  todoViewModel: ToDoViewModel = viewModel()
) {

  val focusModifier =
    if (focusRequester != null)
      Modifier.focusRequester(focusRequester)
    else
      Modifier

  LazyColumn(modifier = modifier) {
    stickyHeader {
      AddTaskInput(
        modifier = if (!showAddTaskInput) focusModifier.height(0.dp).clipToBounds() else focusModifier
      ) {
        s -> todoViewModel.addTask(s)
        onDismiss()
      }
    }

    items(
      items = todoViewModel.taskList,
      key = { task -> task.id }
    ) { task ->
      TaskCard(
        task = task,
        toggleCompleted = { t ->
          todoViewModel.toggleTaskCompleted(t)
        }
      )
    }
  }
}

@Composable
fun AddTaskInput(
  modifier: Modifier = Modifier,
  onEnterTask: (String) -> Unit,
) {
  val keyboardController = LocalSoftwareKeyboardController.current
  var taskBody by remember { mutableStateOf("") }

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

@Composable
fun DeleteTasksDialog(
  onConfirm: () -> Unit,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = {
      onDismiss()
    },
    title = {
      Text("Delete all completed tasks?")
    },
    confirmButton = {
      Button(
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.primary
        ),
        onClick = {
          onConfirm()
        }) {
        Text("Yes")
      }
    },
    dismissButton = {
      Button(
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.secondary
        ),
        onClick = {
          onDismiss()
        }) {
        Text("No")
      }
    },
  )
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