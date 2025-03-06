package dev.csse.kubiak.sensorsdemo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GyroscopeScreen(
  modifier: Modifier = Modifier,
  model: SensorViewModel = viewModel(
    factory = SensorViewModel.Factory
  )
) {
  LifecycleResumeEffect(Unit) {
    model.startGyro()

    onPauseOrDispose {
      model.stopGyro()
    }
  }

  Box(modifier = modifier.fillMaxSize() ) {
    ThreeDValue(
      model.gyroValues,
      modifier = Modifier.fillMaxWidth(),
    )
  }
}