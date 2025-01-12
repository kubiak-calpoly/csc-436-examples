package dev.csse.kubiak.demoweek2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.csse.kubiak.demoweek2.ui.theme.DemoWeek2Theme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      var clickCount by remember { mutableStateOf(1) }
      ClickCounter(clickCount, { clickCount++ })
    }
  }
}

@Composable
fun Greeting(names: List<String>): Unit {
  for ( name in names ) {
    Text(text = "Hello $name!")
  }
}

@Preview(showBackground = true)
@Composable
fun Greeting4Preview() {
  val names = listOf("Paul", "John", "George", "Ringo")
  Greeting(names)
}

@Preview(showBackground = true)
@Composable
fun Greeting1Preview() {
  val names = listOf("Kotlin")
  Greeting(names)
}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
  Button(onClick = onClick) {
    Text("I've been clicked $clicks times")
  }
}

@Preview(showBackground = true)
@Composable
fun CounterPreview() {
  var clickCount by remember { mutableStateOf(1) }
  ClickCounter(clickCount, { clickCount++ })
}

@Preview
@Composable
fun GradScreen() {
  Text(
    text = "Graduation Announcement",
    color = Color.Red,
    fontSize = 50.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 50.sp
  )
}