package dev.csse.kubiak.graphicsdemo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.graphicsdemo.ui.theme.GraphicsDemoTheme


@Composable
fun JellyfishScreen() {

  val vectorPainter = rememberVectorPainter(
    defaultWidth = 530.46f.dp,
    defaultHeight = 563.1f.dp,
    viewportWidth = 530.46f,
    viewportHeight = 563.1f,
    autoMirror = true,
  ) { _, _ ->

  }

  Image(
    vectorPainter,
    contentDescription = "Jellyfish",
    modifier = Modifier
      .fillMaxSize()
      .background(Color.Blue)
  )
}

@Preview
@Composable
fun JellyfishPreview() {
  GraphicsDemoTheme {
    JellyfishScreen()
  }
}

// create a custom gradient background that has a radius that is the size of the biggest dimension of the drawing area, this creates a better looking radial gradient in this case.
val largeRadialGradient = object : ShaderBrush() {
  override fun createShader(size: Size): Shader {
    val biggerDimension = maxOf(size.height, size.width)
    return RadialGradientShader(
      colors = listOf(Color(0xFF2be4dc), Color(0xFF243484)),
      center = size.center,
      radius = biggerDimension / 2f,
      colorStops = listOf(0f, 0.95f)
    )
  }
}