package dev.csse.kubiak.sensorsdemo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.callbackFlow

@Composable
fun SensorInventoryScreen(
  modifier: Modifier = Modifier,
  model: SensorViewModel = viewModel(
    factory = SensorViewModel.Factory
  )
) {
  val sensorList = model.getSensorList()

  Column(modifier = modifier
    .verticalScroll(rememberScrollState()) ) {
    sensorList.forEach {
      Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween ) {
        Text(it.name)
        Text(it.type.toString())
      }
    }
  }
}