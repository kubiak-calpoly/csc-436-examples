package dev.csse.kubiak.demoweek4
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dev.csse.kubiak.demoweek4.ui.ToDoScreen
import dev.csse.kubiak.demoweek4.ui.theme.DemoWeek4Theme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         DemoWeek4Theme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               ToDoScreen()
            }
         }
      }
   }
}
