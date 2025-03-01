package dev.csse.kubiak.demoweek9.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek9.data.WeatherReport

@Composable
fun WeatherScreen(
  lat: Float,
  lon: Float,
  modifier: Modifier = Modifier,
  model: WeatherViewModel = viewModel(
    factory = WeatherViewModel.Factory
  )
) {
  val uiState = model.uiState

  LaunchedEffect(lat, lon) {
    model.getWeather(lat, lon)
  }

  when (uiState) {
    is WeatherUiState.Loading -> Text("Loading...")
    is WeatherUiState.Success -> WeatherView(
      uiState.report, modifier = modifier
    )
    is WeatherUiState.Error -> Text("Error: ${uiState.error}")
  }
}

@Composable
fun WeatherView(
  report: WeatherReport,
  modifier: Modifier = Modifier
) {
  Log.d("WeatherView", "Report: ${report}")

  Column(modifier = modifier.padding(12.dp, 0.dp)) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text("City")
      Text(report.name ?: "--")
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text("GPS Position")
      Column ( horizontalAlignment =  Alignment.End ) {
        Text("Lat: " + (report.coord?.lat?.toString() ?: "--"))
        Text("Lon: " + (report.coord?.lon?.toString() ?: "--"))
      }
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text("Conditions")
      Column ( horizontalAlignment =  Alignment.End ) {
        if (report.weather != null) {
          report.weather.map { w ->
            Text("${w.main} [${w.icon}]")
          }
        } else {
          Text("--")
        }
      }
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text("Current Temp")
      Text((report.main?.temp?.toString() ?: "--") + "°F")
    }
  }
}