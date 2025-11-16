package dev.csse.kubiak.WeatherDemo.data

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat.getString
import dev.csse.kubiak.WeatherDemo.R

class WeatherRepository(
  val context: Context,
  val service: WeatherService
) {

  // TODO: val API_KEY =

  suspend fun getWeather(lat: Float, lon: Float): WeatherReport {
    Log.d("Weather Repository", "Getting the weather at $lat, $lon")
    return service.getWeather(lat, lon, apiKey = API_KEY)
  }
}