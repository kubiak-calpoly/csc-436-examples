package dev.csse.kubiak.demoweek9.ui

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

const val LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION

@Composable
fun LocalScreen(
  modifier: Modifier = Modifier,
  model: LocationViewModel = viewModel()
) {
  val context = LocalContext.current
  var hasPermission by remember { mutableStateOf(false) }
  var currentPosition: LatLng? by remember {
    mutableStateOf(null)
  }

  val permissionLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
  ) { isGranted -> hasPermission = isGranted }

  LaunchedEffect(Unit) {
    if (ContextCompat.checkSelfPermission(
        context, LOCATION_PERMISSION
      ) == PackageManager.PERMISSION_GRANTED
    ) {
      hasPermission = true
    } else {
      permissionLauncher.launch(LOCATION_PERMISSION)
    }
  }

  if (hasPermission) {
    val locationClient = remember {
      LocationServices.getFusedLocationProviderClient(context)
    }

    LaunchedEffect(Unit) {
      withContext(Dispatchers.IO) {
        val currentLocation = locationClient.getCurrentLocation(
          Priority.PRIORITY_HIGH_ACCURACY,
          CancellationTokenSource().token
        ).await()
        if (currentLocation != null) {
          Log.i(
            "LocalScreen", "Current location: " +
                    "lat: ${currentLocation.latitude} " +
                    "lon: ${currentLocation.longitude}"
          )
          currentLocation.let {
            currentPosition =
              LatLng(it.latitude, it.longitude)
          }
        }
      }
    }

    if (currentPosition == null) {
      Card(modifier = modifier) {
        Text("Acquiring location…")
      }
    } else {
      WeatherScreen(
        currentPosition!!.latitude.toFloat(),
        currentPosition!!.longitude.toFloat(),
        modifier = modifier
      )
    }

  } else {
    Card(modifier = modifier) {
      Text("You must allow access to Location services to use this feature.")
    }
  }
}

