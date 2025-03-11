package dev.csse.kubiak.sensorsdemo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CompassScreen(
  modifier: Modifier = Modifier,
  model: SensorViewModel = viewModel(
    factory = SensorViewModel.Factory
  )
) {
<<<<<<< Updated upstream
=======
  val rotation = model.compassRotation

  LifecycleResumeEffect(Unit) {
    model.startCompass()

    onPauseOrDispose {
      model.stopCompass()
    }
  }

>>>>>>> Stashed changes
  val vectorPainter = rememberVectorPainter(
    defaultWidth = 100.dp,
    defaultHeight = 100.dp,
    viewportWidth = 100f,
    viewportHeight = 100f,
    autoMirror = false
  ) { vw: Float, vh: Float ->
    val cx = vw / 2f
    val cy = vh / 2f
    val r = cx * 0.8f
    val tail = r / 5f

    Group {
      val circlePathNodes = listOf(
        PathNode.MoveTo(cx + r, cy),
        PathNode.ArcTo(r, r, 0f, false, false, cx - r, cy),
        PathNode.ArcTo(r, r, 0f, false, false, cx + r, cy),
        PathNode.Close
      )

      Path(
        circlePathNodes,
        fill = SolidColor(Color(0x180880f0)),
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 5f
      )

      val needlePathNodes = listOf(
        PathNode.MoveTo(cx, cy - r),
        PathNode.LineTo(cx - tail, cy + tail),
        PathNode.LineTo(cx, cy),
        PathNode.LineTo(cx + tail, cy + tail),
        PathNode.Close
      )
      Path(
        needlePathNodes,
        fill = SolidColor(Color.Black)
      )
    }
  }

  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = vectorPainter,
      contentDescription = "Compass",
      modifier = Modifier
        .size(200.dp)
        .rotate(model.compassRotation)
    )
  }

  LifecycleResumeEffect(Unit) {
    model.startCompass()

    onPauseOrDispose {
      model.stopCompass()
    }
  }

}