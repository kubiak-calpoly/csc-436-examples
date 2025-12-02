package dev.csse.kubiak.WeatherDemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.csse.kubiak.WeatherDemo.ui.WeatherApp
import dev.csse.kubiak.WeatherDemo.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      WeatherAppTheme {
        WeatherApp()
      }
    }
  }
}

