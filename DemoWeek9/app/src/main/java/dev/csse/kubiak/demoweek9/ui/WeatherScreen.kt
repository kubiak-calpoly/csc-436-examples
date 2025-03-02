package dev.csse.kubiak.demoweek9.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek9.data.WeatherReport
import dev.csse.kubiak.demoweek9.R
import org.w3c.dom.Text

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
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(12.dp, 0.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center
    ) {
      Text(report.name ?: "--", style = MaterialTheme.typography.titleLarge)
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        val lat = report.coord?.lat
        Text(
          if (lat != null) String.format("%.4f", lat)
          else "--")
        Text("LAT", style = MaterialTheme.typography.labelSmall
        )
      }
      Column(
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        val lon = report.coord?.lon
        Text(
          if (lon != null) String.format("%.4f", lon)
          else "--")
        Text("LON", style = MaterialTheme.typography.labelSmall)
      }
    }
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .weight(.5f),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      report.weather?.forEach {
        Text("${it.main}: ${it.description}")
      }
    }
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .scale(2.5f),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      val temp = report.main?.temp
      Text(
        if (temp != null) String.format("%.0f", temp) else "--",
        style = MaterialTheme.typography.displayLarge
              )
      Text(
        "°F",
        style = MaterialTheme.typography.labelSmall
      )
    }
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .weight(.5f),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(
         horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =  Arrangement.SpaceAround
      ) {
        val speed = report.wind?.speed
        Text(
          if (speed != null) String.format("%.0f", speed)
          else "--",
          style = MaterialTheme.typography.displayMedium,
          modifier = Modifier.weight(1f)
        )
        Text("MPH",
          style = MaterialTheme.typography.labelSmall
        )
      }
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =  Arrangement.SpaceAround
      ) {
        val dir = report.wind?.deg

        Icon(
          painterResource(R.drawable.baseline_arrow_upward_24),
          contentDescription = "arrow pointing $dir degrees from North",
          modifier = Modifier
            .weight(1f)
            .scale(2.5f)
            .rotate(180f + (dir ?: 0f))
        )
        Text(
          if (dir != null) String().format("%.0°", dir)
          else "--",
          style = MaterialTheme.typography.labelSmall
        )
      }
    }
  }

}
