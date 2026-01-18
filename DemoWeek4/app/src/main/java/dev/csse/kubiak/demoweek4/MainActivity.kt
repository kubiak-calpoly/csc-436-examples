package dev.csse.kubiak.demoweek4
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek4.ui.ToDoScreen
import dev.csse.kubiak.demoweek4.ui.ToDoViewModel
import dev.csse.kubiak.demoweek4.ui.theme.DemoWeek4Theme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
        val model: ToDoViewModel = viewModel()
        if(model.taskList.isEmpty())  model.createTestTasks(50)
         DemoWeek4Theme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               ToDoScreen(model = model)
            }
         }
      }
   }
}
