package dev.csse.kubiak.sensorsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.tooling.preview.Preview
import dev.csse.kubiak.sensorsdemo.ui.SensorApp
import dev.csse.kubiak.sensorsdemo.ui.theme.SensorsDemoTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      SensorsDemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          SensorApp(modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}
