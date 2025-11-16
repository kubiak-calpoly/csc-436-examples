package dev.csse.kubiak.WeatherDemo.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.csse.kubiak.WeatherDemo.data.WeatherRepository
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.csse.kubiak.WeatherDemo.WeatherApplication
import dev.csse.kubiak.WeatherDemo.data.WeatherReport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class WeatherUiState {
  // TODO: 3 options for WeatherUiState
}

class WeatherViewModel(
  private val weatherRepository: WeatherRepository
) : ViewModel() {
  // TODO: var uiState: WeatherUiState

  var lat: Float? = null
  var lon: Float? = null

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as WeatherApplication)
        WeatherViewModel(application.weatherRepository)
      }
    }
  }

  fun getWeather(lat: Float, lon: Float) {
    this.lat = lat
    this.lon = lon
    viewModelScope.launch(Dispatchers.IO) {
      // TODO: uiState =
      try {
        // TODO: get weather, return Success
      } catch (e: Exception) {
        // TODO: return an error
      }
    }
  }

  fun getWeatherAgain() {
    if ( isSomewhere() )
      getWeather(this.lat!!, this.lon!!)
  }

  fun isSomewhere(): Boolean  {
    return this.lat != null && this.lon != null
  }
}

