package dev.csse.kubiak.demoweek9.data

import android.content.Context
import android.util.Log
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.getString
import dev.csse.kubiak.demoweek9.R

class WeatherRepository(
  val context: Context,
  val service: WeatherService
) {
  val API_KEY = getString(context, R.string.com_openweatherapp_api_key);

  suspend fun getWeather(lat: Float, lon: Float): WeatherReport {
    return service.getWeather(lat, lon, apiKey = API_KEY)
  }
}