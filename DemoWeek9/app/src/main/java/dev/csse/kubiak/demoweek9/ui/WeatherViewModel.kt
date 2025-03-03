package dev.csse.kubiak.demoweek9.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.csse.kubiak.demoweek9.data.WeatherRepository
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.maps.model.LatLng
import dev.csse.kubiak.demoweek9.WeatherApplication
import dev.csse.kubiak.demoweek9.data.WeatherReport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(
  // private val weatherRepository: WeatherRepository
) : ViewModel() {

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as WeatherApplication)
        WeatherViewModel(
          //application.weatherRepository
        )
      }
    }
  }

  fun getWeather(lat: Float, lon: Float) {
  }
}

