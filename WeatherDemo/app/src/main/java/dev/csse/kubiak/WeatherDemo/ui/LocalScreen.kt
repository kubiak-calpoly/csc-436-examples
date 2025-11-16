package dev.csse.kubiak.WeatherDemo.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun LocalScreen(
  modifier: Modifier = Modifier,
  model: LocationViewModel = viewModel()
) {
  val context = LocalContext.current

  val permissionLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
  ) { isGranted -> model.hasPermission = isGranted }

  LaunchedEffect(model.hasPermission) {
    if ( model.hasPermission ) {
      model.createClient(context)
      model.acquireLocation()
    } else {
      model.requestPermission(context, permissionLauncher)
    }
  }

  if(model.hasPermission) {
    val coords = model.currentLocation

    if (coords == null) {
      Card(modifier = modifier) {
        Text("Acquiring location…")
      }
    } else {
      WeatherScreen(
        coords.latitude.toFloat(),
        coords.longitude.toFloat(),
        modifier = modifier
      )
    }

  } else {
    Card(modifier = modifier) {
      Text("You must allow access to Location services to use this feature.")
    }
  }
}

