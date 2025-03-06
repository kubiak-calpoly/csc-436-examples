package dev.csse.kubiak.sensorsdemo.ui

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.Path

import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.translationMatrix

@Composable
fun ThreeDValue(
  values: List<Float>,
  modifier: Modifier = Modifier,
  labels: List<String> = listOf("X", "Y", "Z"),
  name: String = "3D Value",
  scale: Float = 10f
) {

  val r = 10f

  val dot = listOf(
    PathNode.MoveTo(r, 0f),
    PathNode.ArcTo(r, r, 0f, false, false, -r, 0f),
    PathNode.ArcTo(r, r, 0f, false, false, r, 0f),
    PathNode.Close
  )

  val xyPainter = rememberVectorPainter(
    defaultWidth = 200.dp,
    defaultHeight = 200.dp,
    autoMirror = false
  ) { vw: Float, vh: Float ->

    val x = values[0] * scale
    val y = values[1] * scale

    val xAxis = listOf(
      PathNode.MoveTo(-vw / 2, 0f),
      PathNode.LineTo(vw / 2, 0f)
    )

    val yAxis = listOf(
      PathNode.MoveTo(0f, -vh / 2),
      PathNode.LineTo(0f, vh / 2)
    )

    val xyVector = listOf(
      PathNode.MoveTo(0f, 0f),
      PathNode.LineTo(x, y)
    )

    Group(translationX = vw / 2, translationY = vh / 2) {
      Path(xAxis, stroke = SolidColor(Color.Black))
      Path(yAxis, stroke = SolidColor(Color.Black))
      Path(xyVector, stroke = SolidColor(Color.DarkGray),
        strokeLineWidth = 5f )
      Group(translationX = x, translationY = y) {
        Path(dot, fill = SolidColor(Color.Red))
      }
    }
  }

  val zPainter = rememberVectorPainter(
    defaultWidth = 20.dp,
    defaultHeight = 200.dp,
    autoMirror = false
  ) { vw: Float, vh: Float ->
    val z = values[2] * scale

    val zAxis = listOf(
      PathNode.MoveTo(0f, -vh / 2),
      PathNode.LineTo(0f, vh / 2)
    )

    val zVector = listOf(
      PathNode.MoveTo(0f, 0f),
      PathNode.LineTo(0f, z)
    )

    Group(translationX = vw / 2, translationY = vh / 2) {
      Path(zAxis, stroke = SolidColor(Color.Black))
      Path(
        zVector, stroke = SolidColor(Color.DarkGray),
        strokeLineWidth = 5f
      )
      Group(translationY = z) {
        Path(dot, fill = SolidColor(Color.Red))
      }
    }
  }

  Column(modifier = modifier.fillMaxSize()) {
    Row(modifier = Modifier.weight(1f)) {
      Image(
        xyPainter,
        contentDescription = "XY Chart of ${name}",
        modifier = Modifier.aspectRatio(1f).fillMaxHeight()
      )
      Image(
        zPainter,
        contentDescription = "XY Chart of ${name}",
        modifier = Modifier.fillMaxHeight().aspectRatio(0.1f)
      )
    }
    values.forEachIndexed { index, value ->
      Row(modifier = Modifier.fillMaxWidth()) {
        Text(
          labels[index],
          modifier = Modifier.weight(1f),
          style = MaterialTheme.typography.displayLarge
        )
        Text(
          String.format("%.6f", value),
          modifier = Modifier.weight(7f),
          style = MaterialTheme.typography.displayMedium,
          textAlign = TextAlign.Right
        )
      }
    }

  }
}
