package dev.csse.kubiak.demoweek9.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek9.Greeting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(
  viewModel: WeatherViewModel = viewModel(
    factory = WeatherViewModel.Factory
  )
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("SLO Sky") },
        actions = {
          IconButton(onClick = { viewModel.getWeather() }) {
            Icon(
              Icons.Filled.Refresh,
              contentDescription = "Refresh",
            )
          }
        }
      )
    },
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    WeatherScreen(
      modifier = Modifier.padding(innerPadding)
    )
  }

}