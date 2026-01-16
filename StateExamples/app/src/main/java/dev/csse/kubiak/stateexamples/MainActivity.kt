package dev.csse.kubiak.stateexamples

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.tooling.preview.Preview
import dev.csse.kubiak.stateexamples.ui.theme.StateExamplesTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      StateExamplesTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
          ) {
            ButtonClickExample(
              modifier = Modifier.align(Alignment.Center)
            )
          }
        }
      }
    }
  }
}

@Composable
fun ButtonClickExample(
  modifier: Modifier = Modifier
) {
  var clickCount by remember { mutableStateOf(0) }

  Column(modifier = modifier) {
    Button(
      onClick = {
        clickCount++
        Log.d(
          "ButtonClickExample",
          "onClick called, count = $clickCount"
        )
      }
    ) {
      Text("Click Me")
    }
    Text("Click count = $clickCount")
  }
}

@Preview(showBackground = true)
@Composable
fun ButtonClickPreview() {
  StateExamplesTheme {
    ButtonClickExample()
  }
}