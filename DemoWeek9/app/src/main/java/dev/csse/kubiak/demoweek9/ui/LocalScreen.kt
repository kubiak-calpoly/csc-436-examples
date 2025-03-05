package dev.csse.kubiak.demoweek9.ui

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


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
      // model.createClient(context)
      // model.acquireLocation()
    } else {
      model.requestPermission(context, permissionLauncher)
    }
  }

  if(model.hasPermission) {
    val coords: LatLng? = null

    if (coords == null) {
      Card(modifier = modifier) {
        Text("Acquiring location…",
          modifier = Modifier.padding(12.dp),
          textAlign = TextAlign.Center
        )
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
      Text(
        "You must allow access to Location services to use this feature.",
        modifier = Modifier.padding(12.dp),
        textAlign = TextAlign.Center
      )
    }
  }
}

