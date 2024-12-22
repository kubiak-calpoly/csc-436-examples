package dev.csse.kubiak.datavizcompose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.star
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
  val shapeA = remember {
    RoundedPolygon(
      12,
      rounding = CornerRounding(0.2f)
    )
  }
  val shapeB = remember {
    RoundedPolygon.star(
      12,
      rounding = CornerRounding(0.2f)
    )
  }
  val morph = remember {
    Morph(shapeA, shapeB)
  }
  val infiniteTransition = rememberInfiniteTransition("infinite outline movement")
  val animatedProgress = infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      tween(2000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "animatedMorphProgress"
  )
  val animatedRotation = infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      tween(6000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "animatedMorphProgress"
  )
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = painterResource(id = R.drawable.dog),
      contentDescription = "Dog",
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .clip(
          CustomRotatingMorphShape(
            morph,
            animatedProgress.value,
            animatedRotation.value
          )
        )
        .size(200.dp)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun DataVizPreview() {
  DataVizComposeTheme {
    DataViz("Android")
  }
}

class MorphPolygonShape(
  private val morph: Morph,
  private val percentage: Float
) : Shape {

  private val matrix = Matrix()
  override fun createOutline(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density
  ): Outline {
    // Below assumes that you haven't changed the default radius of 1f, nor the centerX and centerY of 0f
    // By default this stretches the path to the size of the container, if you don't want stretching, use the same size.width for both x and y.
    matrix.scale(size.width / 2f, size.height / 2f)
    matrix.translate(1f, 1f)

    val path = morph.toPath(progress = percentage).asComposePath()
    path.transform(matrix)
    return Outline.Generic(path)
  }
}

class CustomRotatingMorphShape(
  private val morph: Morph,
  private val percentage: Float,
  private val rotation: Float
) : Shape {

  private val matrix = Matrix()
  override fun createOutline(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density
  ): Outline {
    // Below assumes that you haven't changed the default radius of 1f, nor the centerX and centerY of 0f
    // By default this stretches the path to the size of the container, if you don't want stretching, use the same size.width for both x and y.
    matrix.scale(size.width / 2f, size.height / 2f)
    matrix.translate(1f, 1f)
    matrix.rotateZ(rotation)

    val path = morph.toPath(progress = percentage).asComposePath()
    path.transform(matrix)

    return Outline.Generic(path)
  }
}