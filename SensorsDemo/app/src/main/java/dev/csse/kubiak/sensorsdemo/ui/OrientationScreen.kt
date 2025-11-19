package dev.csse.kubiak.sensorsdemo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun OrientationScreen(
  modifier: Modifier = Modifier,
  model: SensorViewModel = viewModel(
    factory = SensorViewModel.Factory
  )
) {
  LifecycleResumeEffect(Unit) {
    model.startOrientation()

    onPauseOrDispose {
      model.stopOrientation()
    }
  }

  val data = model.orientationValues

  val theta = 2f * acos(data.getOrElse(3){1f})
  val x = data[0] / sin(theta/2f)
  val y = data[1] / sin(theta/2f)
  val z = data[2] / sin(theta/2f)
  val unit = sqrt(x*x + y*y + z*z)

  Box(modifier = modifier.fillMaxSize() ) {
    ThreeDValue(
      listOf(x,y,z,theta * 180/PI.toFloat()),
      modifier = Modifier.fillMaxWidth(),
      scale = 90f/unit
    )
  }
}