package dev.csse.kubiak.graphicsdemo.ui

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.collection.emptyLongSet
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.graphicsdemo.ui.theme.GraphicsDemoTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun JellyfishScreen(
  model: JellyfishViewModel = viewModel()
) {
  val coroutineScope = rememberCoroutineScope()
  val vectorPainter = rememberVectorPainter(
    defaultWidth = 530.46f.dp,
    defaultHeight = 563.1f.dp,
    viewportWidth = 530.46f,
    viewportHeight = 563.1f,
    autoMirror = true,
  ) { viewPortWidth, viewPortHeight ->
    val duration = 3000
    val transition = rememberInfiniteTransition()
    val translationY by transition.animateFloat(
      initialValue = 0f, targetValue = -30f, animationSpec = infiniteRepeatable(
        tween(duration, easing = EaseInOut), repeatMode = RepeatMode.Reverse
      )
    )

    Group(
      name = "jellyfish", translationY = translationY
    ) {
      Group("tentacles") {
        val alphas = arrayOf(.49f, .66f, .44f, .6f)
        model.tentacles.forEachIndexed { i, path ->
          Path(pathData = path,
            fill = SolidColor(Color.White),
            fillAlpha = alphas.getOrElse(i) { 1f })
        }
      }
      Group(name = "body") {
        val alphas = arrayOf(1f, .5f)
        model.body.forEachIndexed { i, path ->
          Path(pathData = path,
            fill = SolidColor(Color.White),
            fillAlpha = alphas.getOrElse(i) { 1f })
        }
      }
      Group(name = "freckles") {
        // freckle paths
        model.freckles.forEachIndexed { i, path ->
          Path(
            pathData = path, fill = SolidColor(
              Color(0xfff0dfe2)
            ), fillAlpha = 1f
          )
        }
      }
    }

    Group(name = "bubbles") {
      // bubbles around the jellyfish
      val alphas = arrayOf(.67f, .79f, .89f, .77f, .77f)
      model.bubbles.forEachIndexed { i, path ->
        Path(pathData = path,
          fill = SolidColor(Color.White),
          fillAlpha = alphas.getOrElse(i) { .49f })
      }
    }
  }

  val blinkAlphaAnimation = remember {
    Animatable(1f)
  }
  val blinkScaleAnimation = remember {
    Animatable(1f)
  }

  suspend fun instantBlinkAnimation() {
    val tweenSpec = tween<Float>(150, easing = LinearEasing)
    coroutineScope {
      launch {
        blinkAlphaAnimation.animateTo(0f, animationSpec = tweenSpec)
        blinkAlphaAnimation.animateTo(1f, animationSpec = tweenSpec)
      }
      launch {
        blinkScaleAnimation.animateTo(0.3f, animationSpec = tweenSpec)
        blinkScaleAnimation.animateTo(1f, animationSpec = tweenSpec)
      }
    }
  }

  val vectorPainterFace = rememberVectorPainter(
    defaultWidth = 530.46f.dp,
    defaultHeight = 563.1f.dp,
    viewportWidth = 530.46f,
    viewportHeight = 563.1f,
    autoMirror = true,
  ) { _, _ ->
    val duration = 3000
    val transition = rememberInfiniteTransition()
    val translationY by transition.animateFloat(
      initialValue = 0f, targetValue = -30f, animationSpec = infiniteRepeatable(
        tween(duration, easing = EaseInOut), repeatMode = RepeatMode.Reverse
      )
    )
    Group(name = "face",
      translationY = translationY) {
      // face paths
      Group(
        name = "eye-left",
        scaleY = blinkScaleAnimation.value,
        pivotY = 233f // vertical center of eye path
      ) {
        Path(
          pathData = model.face[0],
          fill = SolidColor(Color(0xFFb4bebf)),
          fillAlpha = blinkAlphaAnimation.value,
        )
      }
      Group(
        name = "eye-right",
        scaleY = blinkScaleAnimation.value,
        pivotY = 233f // vertical center of eye path
      ) {
        Path(
          pathData = model.face[1],
          fill = SolidColor(Color(0xFFb4bebf)),
          fillAlpha = blinkAlphaAnimation.value,
        )
      }
      Path(
        pathData = model.face[2],
        fill = SolidColor(Color(0xFFd3d3d3)),
        fillAlpha = 1f,
      )
    }
  }

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    WobblyImage(
      vectorPainter,
      contentDescription = "Jellyfish",
      modifier = Modifier.fillMaxSize()
        .background(largeRadialGradient)
      ,
      shader = PERLIN_NOISE
    )
  }
   else {
    Image(
      vectorPainter,
      contentDescription = "Jellyfish",
      modifier = Modifier.fillMaxSize()
        .background(largeRadialGradient)
    )
  }

  Image(vectorPainterFace,
    contentDescription = "",
    modifier = Modifier
      .fillMaxSize()
      .pointerInput(Unit) {
        detectTapGestures {
          coroutineScope.launch {
            instantBlinkAnimation()
          }
        }
      })

}

@Preview
@Composable
fun JellyfishPreview() {
  GraphicsDemoTheme {
    JellyfishScreen()
  }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun WobblyImage(
  painter: VectorPainter,
  contentDescription: String?,
  modifier: Modifier = Modifier,
  shader: String = WOBBLE_SHADER
) {
  val time by produceState(0f) {
    while (true) {
      withInfiniteAnimationFrameMillis {
        value = it / 1000f
      }
    }
  }

  val shader = RuntimeShader(shader)

  Image(
    painter,
    contentDescription = "Jellyfish",
    modifier = modifier

      .onSizeChanged { size ->
        shader.setFloatUniform(
          "resolution",
          size.width.toFloat(),
          size.height.toFloat()
        )
      }
      .graphicsLayer {
        shader.setFloatUniform("time", time)
        renderEffect = android.graphics.RenderEffect
          .createRuntimeShaderEffect(
            shader,
            "contents"
          )
          .asComposeRenderEffect()
      }
  )
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