package dev.csse.kubiak.datavizcompose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
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
import kotlin.math.max

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
      6,
      rounding = CornerRounding(0.2f)
    )
  }
  val shapeB = remember {
    RoundedPolygon.star(
      6,
      rounding = CornerRounding(0.1f)
    )
  }
  val morph = remember {
    Morph(shapeA, shapeB)
  }
  val interactionSource = remember {
    MutableInteractionSource()
  }
  val isPressed by interactionSource.collectIsPressedAsState()
  val animatedProgress = animateFloatAsState(
    targetValue = if (isPressed) 1f else 0f,
    label = "progress",
    animationSpec = spring(dampingRatio = 0.4f, stiffness = Spring.StiffnessMedium)
  )
  Box(
    modifier = Modifier
      .size(200.dp)
      .padding(8.dp)
      .clip(MorphPolygonShape(morph, animatedProgress.value))
      .background(Color(0xFF80DEEA))
      .size(200.dp)
      .clickable(interactionSource = interactionSource, indication = null) {
      }
  ) {
    Text("Hello", modifier = Modifier.align(Alignment.Center))
  }
}

@Preview(showBackground = true)
@Composable
fun DataVizPreview() {
  DataVizComposeTheme {
    DataViz("Android")
  }
}

fun RoundedPolygon.getBounds() = calculateBounds().let {
  Rect(it[0], it[1], it[2], it[3]) }
class RoundedPolygonShape(
  private val polygon: RoundedPolygon,
  private var matrix: Matrix = Matrix()
) : Shape {
  private var path = Path()
  override fun createOutline(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density
  ): Outline {
    path.rewind()
    path = polygon.toPath().asComposePath()
    matrix.reset()
    val bounds = polygon.getBounds()
    val maxDimension = max(bounds.width, bounds.height)
    matrix.scale(size.width / maxDimension, size.height / maxDimension)
    matrix.translate(-bounds.left, -bounds.top)

    path.transform(matrix)
    return Outline.Generic(path)
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