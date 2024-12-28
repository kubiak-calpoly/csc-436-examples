package dev.csse.kubiak.datavizcompose
import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalDensity
import kotlin.math.min

private const val animationDuration = 800
private const val chartDegrees = 360f
private const val emptyIndex = -1

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
internal fun PieChart(
  modifier: Modifier = Modifier,
  colors: List<Color>,
  inputValues: List<Float>,
  textColor: Color = MaterialTheme.colorScheme.primary,
  animated: Boolean = true,
  enableClickInfo: Boolean = true
) {
  val chartDegrees = 360f // circle shape

  // start drawing clockwise (top to right)
  var startAngle = 270f

  // calculate each input percentage
  val proportions = inputValues.map {
    it * 100 / inputValues.sum()
  }

  // calculate each input slice degrees
  val angleProgress = proportions.map { prop ->
    chartDegrees * prop / 100
  }

  // clicked slice index
  var clickedItemIndex by remember {
    mutableIntStateOf(emptyIndex)
  }

  // calculate each slice end point in degrees, for handling click position
  val progressSize = mutableListOf<Float>()

  LaunchedEffect(angleProgress){
    progressSize.add(angleProgress.first())
    for (x in 1 until angleProgress.size) {
      progressSize.add(angleProgress[x] + progressSize[x - 1])
    }
  }

  BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {

    val canvasSize = min(constraints.maxWidth, constraints.maxHeight)
    val size = Size(canvasSize.toFloat(), canvasSize.toFloat())
    val canvasSizeDp = with(LocalDensity.current) { canvasSize.toDp() }

    Canvas(modifier = Modifier.size(canvasSizeDp)) {

      angleProgress.forEachIndexed { index, angle ->
        drawArc(
          color = colors[index],
          startAngle = startAngle,
          sweepAngle = angle,
          useCenter = true,
          size = size,
          style = Fill
        )
        startAngle += angle
      }

    }

  }
}