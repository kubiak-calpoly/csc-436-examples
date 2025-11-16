package dev.csse.kubiak.sensorsdemo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AccelerometerScreen(
  modifier: Modifier = Modifier,
  model: SensorViewModel = viewModel(
    factory = SensorViewModel.Factory
  )
) {
<<<<<<< Updated upstream
=======
  val values = model.accelValues

>>>>>>> Stashed changes
  LifecycleResumeEffect(Unit) {
    model.startAccel()

    onPauseOrDispose {
      model.stopAccel()
    }
  }

  Box(modifier = modifier.fillMaxSize() ) {
    ThreeDValue(
      model.accelValues,
      modifier = Modifier.fillMaxWidth(),
    )
  }
}
