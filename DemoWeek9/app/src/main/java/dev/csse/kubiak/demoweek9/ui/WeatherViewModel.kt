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
import dev.csse.kubiak.demoweek9.WeatherApplication
import dev.csse.kubiak.demoweek9.data.WeatherReport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class WeatherUiState {
  data class Success(val report: WeatherReport ) : WeatherUiState()
  data class Error(val error: String = "error") : WeatherUiState()
  data object Loading : WeatherUiState()
}

class WeatherViewModel(
  private val weatherRepository: WeatherRepository
) : ViewModel() {
  var uiState: WeatherUiState by mutableStateOf(WeatherUiState.Loading)
    private set

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as WeatherApplication)
        WeatherViewModel(application.weatherRepository)
      }
    }
  }

  init {
    getWeather()
  }

  fun getWeather() {
    viewModelScope.launch(Dispatchers.IO) {
      uiState = try {
        WeatherUiState.Success(
          weatherRepository.getWeather()
        )
      } catch (e: Exception) {
        Log.e("WeatherViewModel", "Error: ${e}")
        WeatherUiState.Error(e.toString())
      }
    }
  }

}