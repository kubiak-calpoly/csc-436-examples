package dev.csse.kubiak.demoweek9.ui


import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
  var hasPermission by mutableStateOf(false)
  var currentLocation: LatLng? by mutableStateOf(null)
    private set
  var locationClient: FusedLocationProviderClient? = null
    private set

  fun requestPermission(
    context: Context,
    permissionLauncher:
    ManagedActivityResultLauncher<String, Boolean>
  ) {
    if (ActivityCompat.checkSelfPermission(
        context, ACCESS_FINE_LOCATION
      ) != PERMISSION_GRANTED &&
      ActivityCompat.checkSelfPermission(
        context, ACCESS_COARSE_LOCATION
      ) != PERMISSION_GRANTED
    ) {
      permissionLauncher.launch(ACCESS_FINE_LOCATION)
    } else {
      hasPermission = true
    }
  }

  fun createClient(context: Context) {
    if (locationClient == null)
      locationClient = LocationServices.getFusedLocationProviderClient(context)
  }

  @SuppressLint("MissingPermission")
  fun acquireLocation() {
    viewModelScope.launch(Dispatchers.IO) {
      locationClient?.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        CancellationTokenSource().token
      )?.addOnSuccessListener { location ->
        if (location != null) {
          Log.i(
            "LocalScreen", "Current location: " +
                    "lat: ${location.latitude} " +
                    "lon: ${location.longitude}"
          )
          location.let {
            currentLocation =
              LatLng(it.latitude, it.longitude)
          }
        }
      }
    }
  }
}