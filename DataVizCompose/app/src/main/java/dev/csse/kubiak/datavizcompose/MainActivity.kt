package dev.csse.kubiak.datavizcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.tooling.preview.Preview
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import dev.csse.kubiak.datavizcompose.ui.theme.DataVizComposeTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      DataVizComposeTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          DataViz(
            name = "Android",
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun DataViz(name: String, modifier: Modifier = Modifier) {
  Box(
    modifier = Modifier
      .drawWithCache {
        val roundedPolygon = RoundedPolygon(
          numVertices = 3,
          radius = size.minDimension / 2,
          centerX = size.width / 2,
          centerY = size.height / 2,
          rounding = CornerRounding(
            size.minDimension / 10f,
            smoothing = 0.1f
          )
        )
        val roundedPolygonPath = roundedPolygon.toPath().asComposePath()
        onDrawBehind {
          drawPath(roundedPolygonPath, color = Color.Black)
        }
      }
      .fillMaxSize()
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  DataVizComposeTheme {
    DataViz("Android")
  }
}