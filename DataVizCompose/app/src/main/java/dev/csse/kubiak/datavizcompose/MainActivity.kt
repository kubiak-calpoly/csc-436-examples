package dev.csse.kubiak.datavizcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.graphics.shapes.Morph
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
  val infiniteAnimation = rememberInfiniteTransition(label = "infinite animation")
  val morphProgress = infiniteAnimation.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      tween(500),
      repeatMode = RepeatMode.Reverse
    ),
    label = "morph"
  )
  Box(
    modifier = Modifier
      .drawWithCache {
        val triangle = RoundedPolygon(
          numVertices = 3,
          radius = size.minDimension / 2f,
          centerX = size.width / 2f,
          centerY = size.height / 2f,
          rounding = CornerRounding(
            size.minDimension / 10f,
            smoothing = 0.1f
          )
        )
        val square = RoundedPolygon(
          numVertices = 4,
          radius = size.minDimension / 2f,
          centerX = size.width / 2f,
          centerY = size.height / 2f
        )

        val morph = Morph(start = triangle, end = square)
        val morphPath = morph
          .toPath(progress = morphProgress.value).asComposePath()

        onDrawBehind {
          drawPath(morphPath, color = Color.Black)
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