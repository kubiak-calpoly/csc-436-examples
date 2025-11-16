package dev.csse.kubiak.sensorsdemo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LinearAccelerometerScreen(
  modifier: Modifier = Modifier,
  model: SensorViewModel = viewModel(
    factory = SensorViewModel.Factory
  )
) {
  LifecycleResumeEffect(Unit) {
    model.startLinear()

    onPauseOrDispose {
      model.stopLinear()
    }
  }

  Box(modifier = modifier.fillMaxSize() ) {
    ThreeDValue(
      model.linearValues,
      modifier = Modifier.fillMaxWidth(),
    )
  }
}