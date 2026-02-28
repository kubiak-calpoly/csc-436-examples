package dev.csse.kubiak.graphicsdemo.ui

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusModifier
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.graphicsdemo.ui.theme.GraphicsDemoTheme
import dev.csse.kubiak.graphicsdemo.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.collections.get

@Composable
fun JellyfishScreen(
  model: JellyfishViewModel = viewModel(),
  modifier: Modifier = Modifier
) {
  val uiState by model.uiState.collectAsStateWithLifecycle()

  BoxWithConstraints(modifier = modifier) {
    val maxSize = min(maxWidth, maxHeight)
    Bubbles(maxSize, maxSize,
      modifier = Modifier.fillMaxSize()
    )
    Jellyfish(maxSize, maxSize,
      modifier = Modifier.fillMaxWidth()
        .align(Alignment.TopCenter)
    )
    Corals(maxWidth, maxHeight/2,
      modifier = Modifier.fillMaxHeight(.5f)
      .align(Alignment.BottomCenter))
    Row(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom =  20.dp),
      horizontalArrangement = Arrangement.spacedBy(40.dp)) {
      IconButton(
        enabled = !uiState.isMoving,
        onClick = {
          model.startMotion()
        }
      ) {
        Icon(
          painterResource(R.drawable.outline_play_circle_filled_24),
          contentDescription = "Play",
          modifier = Modifier.scale(2.0f),
          tint =  Color.White
        )
      }
      IconButton(
        enabled = uiState.isMoving,
        onClick = { model.pauseMotion() }
      ) {
        Icon(
          painterResource(R.drawable.outline_pause_circle_filled_24),
          contentDescription = "Pause",
          modifier = Modifier.scale(2.0f),
          tint =  Color.White
        )
      }
    }
  }
}

@Composable
fun Jellyfish(
  width: Dp,
  height: Dp,
  modifier: Modifier = Modifier,
  model: JellyfishViewModel = viewModel(),
) {
  val jellyPainter = rememberVectorPainter(
    defaultWidth = width,
    defaultHeight = height,
    viewportWidth = 530.46f,
    viewportHeight = 563.1f,
    autoMirror = true,
  ) { viewPortWidth, viewPortHeight ->

    val duration = 10_000
    val transition = rememberInfiniteTransition()
    val translationY by transition.animateFloat(
      initialValue = 100f, targetValue = -100f, animationSpec = infiniteRepeatable(
        tween(duration, easing = EaseInOut), repeatMode = RepeatMode.Reverse
      )
    )

    Group(
      name = "jellyfish", translationY = translationY
    ) {
      Group("tentacles") {
        val alphas = arrayOf(.49f, .66f, .44f, .6f)
        model.tentacles.forEachIndexed { i, path ->
          Path(
            pathData = path,
            fill = SolidColor(Color.White),
            fillAlpha = alphas.getOrElse(i) { 1f })
        }
      }
      Group(name = "body") {
        val alphas = arrayOf(1f, .5f)
        model.body.forEachIndexed { i, path ->
          Path(
            pathData = path,
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

    Group(
      name = "face",
      translationY = translationY
    ) {
      // face paths
      Group(
        name = "eye-left",
        scaleY = 1f,
        pivotY = 233f // vertical center of eye path
      ) {
        Path(
          pathData = model.face[0],
          fill = SolidColor(Color(0xFFb4bebf)),
          fillAlpha = 1f,
        )
      }
      Group(
        name = "eye-right",
        scaleY = 1f,
        pivotY = 233f // vertical center of eye path
      ) {
        Path(
          pathData = model.face[1],
          fill = SolidColor(Color(0xFFb4bebf)),
          fillAlpha = 1f,
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
      jellyPainter,
      contentDescription = "Jellyfish",
      modifier = modifier,
      shader = PERLIN_NOISE
    )
  } else {
    Image(
      jellyPainter,
      contentDescription = "Jellyfish",
      modifier = modifier
    )
  }
}

@Composable
fun Bubbles(
  width: Dp,
  height: Dp,
  modifier: Modifier = Modifier,
  model: JellyfishViewModel = viewModel()
) {
  val bubblePainter = rememberVectorPainter(
    defaultWidth = width,
    defaultHeight = height,
    viewportWidth = 530.46f,
    viewportHeight = 563.1f,
    autoMirror = true,
  ) { viewPortWidth, viewPortHeight ->
    Group(name = "bubbles") {
      // bubbles around the jellyfish
      val alphas = arrayOf(.67f, .79f, .89f, .77f, .77f)
      model.bubbles.forEachIndexed { i, path ->
        Path(
          pathData = path,
          fill = SolidColor(Color.White),
          fillAlpha = alphas.getOrElse(i) { .49f })
      }
    }
  }

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    WobblyImage(
      bubblePainter,
      contentDescription = "Bubbles",
      modifier = modifier
        .background(largeRadialGradient),
      shader = PERLIN_NOISE
    )
  } else {
    Image(
      bubblePainter,
      contentDescription = "Bubbles",
      modifier = modifier
        .background(largeRadialGradient)
    )
  }
}

@Composable
fun Corals(
  width: Dp,
  height: Dp,
  modifier: Modifier = Modifier,
  model: JellyfishViewModel = viewModel()
) {
  val uiState by model.uiState.collectAsStateWithLifecycle()

  val size = 1200f
  val n = model.corals.size
  val coralPainter = rememberVectorPainter(
    defaultWidth = width,
    defaultHeight = height,
    viewportWidth = size,
    viewportHeight = size,
    autoMirror = true,
  ) { viewPortWidth, viewPortHeight ->
    fun offset(x: Int) = (3.0f * x)

    val translationX = remember { Animatable(offset(uiState.xPosition)) }

    LaunchedEffect(uiState.xPosition, uiState.isMoving) {
      if ( uiState.xPosition == 0 )
        translationX.snapTo(offset(uiState.xPosition))
      translationX.animateTo(offset(uiState.xPosition+1),
        animationSpec = tween(
          durationMillis = model.millisPerTick.toInt(),
          easing = LinearEasing
        ))
    }

    Group(name = "coral_reef",
      translationX = -0.5f * width.value - (translationX.value % (n*size/2))
    ) {
      val colors = arrayOf(
        Color(0xC8F5E29A),
        Color(0xC8AEF59A),
        Color(0xC8F5C49A),
        Color(0xC8F59ADE)
      )
      val corals = model.corals + model.corals
      corals.forEachIndexed { i, path ->
        Group(name = "coral $i",
          translationX = 0.5f * size * i
        ) {
          Path(
            pathData = path,
            fill = SolidColor(colors[i % colors.size])
          )
        }
      }
    }
  }

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    WobblyImage(
      coralPainter,
      contentDescription = "Coral",
      modifier = modifier,
      shader = PERLIN_NOISE
    )
  } else {
    Image(
      coralPainter,
      contentDescription = "Coral",
      modifier = modifier
    )
  }
}

@Preview(widthDp = 450, heightDp = 800)
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