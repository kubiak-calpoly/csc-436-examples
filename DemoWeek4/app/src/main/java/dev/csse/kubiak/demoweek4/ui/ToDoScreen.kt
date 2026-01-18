package dev.csse.kubiak.demoweek4.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalConfiguration

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.csse.kubiak.demoweek4.Task
import dev.csse.kubiak.demoweek4.ui.theme.DemoWeek4Theme

@Composable
fun ToDoScreen(
  modifier: Modifier = Modifier,
  model: ToDoViewModel = viewModel()
) {
  Scaffold() { innerPadding ->
    TaskList(
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
    )
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskList(
  modifier: Modifier = Modifier,
  model: ToDoViewModel = viewModel()
) {
  LazyColumn(modifier = modifier) {
    stickyHeader {
      AddTaskInput(onEnterTask = { s ->
        model.addTask(s)
      })
    }
    items(
      items = model.taskList,
      key = { t -> t.id }
    ) { task ->
      TaskCard(
        task = task,
        toggleCompleted = model::toggleTaskCompleted
      )
    }
  }
}


@Composable
fun AddTaskInput(
  modifier: Modifier = Modifier,
  onEnterTask: (Task) -> Unit,
) {
  val keyboardController = LocalSoftwareKeyboardController.current
  var taskBody by remember { mutableStateOf("") }

  @Composable
  fun TaskTextInput(modifier: Modifier = Modifier) {
    OutlinedTextField(
      modifier = modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(6.dp),
      value = taskBody,
      onValueChange = { taskBody = it },
      label = { Text("Enter task") },
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
      keyboardActions = KeyboardActions(onDone = {
        keyboardController?.hide()
        onEnterTask(Task(body = taskBody))
        taskBody = ""
      })
    )
  }


  TaskTextInput(modifier = modifier)
}

@Composable
fun TaskCard(
  task: Task,
  toggleCompleted: (Task, Boolean) -> Unit,
  modifier: Modifier = Modifier
) {
  Log.d("TaskCard", "Rendering ${task}")

  @Composable
  fun TaskText(modifier: Modifier = Modifier) {
    Text(
      text = task.body,
      style = MaterialTheme.typography.bodyLarge,
      modifier = modifier,
      color = if (task.completed) Color.Gray else Color.Black
    )
  }

  Card(
    modifier = modifier
      .padding(8.dp)
      .fillMaxWidth(), colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant
    )
  ) {
    Row(
      modifier = modifier
        .fillMaxWidth()
        .padding(8.dp, 0.dp),
      verticalAlignment = Alignment.Top,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Checkbox(
        checked = task.completed,
        modifier = Modifier.alignByBaseline(),
        onCheckedChange = { toggleCompleted(task, it) }
      )

      TaskText(
        modifier = Modifier
          .alignByBaseline()
          .padding(12.dp)
          .weight(1f)
      )

    }
  }
}

@Preview(showBackground = true)
@Composable
fun ToDoScreenPreview() {
  val todoViewModel = viewModel<ToDoViewModel>()

  if (todoViewModel.taskList.isEmpty())
    todoViewModel.createTestTasks(5)

  DemoWeek4Theme(dynamicColor = false) {
    ToDoScreen()
  }
}