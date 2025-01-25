package dev.csse.kubiak.demoweek4.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.kubiak.demoweek4.Task
import dev.csse.kubiak.demoweek4.ui.theme.DemoWeek4Theme

@Composable
fun ToDoScreen(
   modifier: Modifier = Modifier,
   todoViewModel: ToDoViewModel = viewModel()
) {
   Column(
      modifier = modifier
         .fillMaxSize()
   ) {
      AddTaskInput(todoViewModel::addTask)
      TaskList(
         taskList = todoViewModel.taskList,
         onDeleteTask = todoViewModel::deleteTask,
         onArchiveTask = todoViewModel::archiveTask,
         onToggleTaskComplete = todoViewModel::toggleTaskCompleted
      )
   }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TaskList(
   taskList: List<Task>,
   onDeleteTask: (Task) -> Unit,
   onArchiveTask: (Task) -> Unit,
   onToggleTaskComplete: (Task) -> Unit
) {
   LazyColumn {
      items(
         items = taskList,
         key = { task -> task.id }
      ) { task ->
         val dismissState = rememberSwipeToDismissBoxState()

         SwipeToDismiss(
            state = dismissState,
            background = { SwipeBackground(dismissState) },
            modifier = Modifier
               .padding(vertical = 1.dp)
               .animateItemPlacement(),
            dismissContent = {
               TaskCard(
                  task = task,
                  toggleCompleted = onToggleTaskComplete
               )
            }
         )
      }
   }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SwipeBackground(
   dismissState: SwipeToDismissBoxState,
   modifier: Modifier = Modifier
) {
   // TODO: Implement later
}

@Composable
fun AddTaskInput(onEnterTask: (String) -> Unit) {
   val keyboardController = LocalSoftwareKeyboardController.current
   var taskBody by remember { mutableStateOf("") }

   OutlinedTextField(
      modifier = Modifier
         .fillMaxWidth()
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
   Text(
      text = task.body,
      fontSize = 26.sp,
      modifier = modifier.padding(start = 12.dp)
   )
}

@Preview(showBackground = true)
@Composable
fun ToDoScreenPreview() {
   val viewModel = ToDoViewModel()
   viewModel.createTestTasks(5)
   DemoWeek4Theme {
      ToDoScreen(todoViewModel = viewModel)
   }
}