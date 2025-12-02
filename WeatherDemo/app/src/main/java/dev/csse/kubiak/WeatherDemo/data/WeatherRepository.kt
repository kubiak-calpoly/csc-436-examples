package dev.csse.kubiak.WeatherDemo.data

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat.getString
import dev.csse.kubiak.WeatherDemo.R

class WeatherRepository(
  val context: Context,
  val service: WeatherService
) {

  val API_KEY = getString(context, R.string.com_openweatherapp_api_key);

  suspend fun getWeather(lat: Float, lon: Float): WeatherReport {
    Log.d("Weather Repository", "Getting the weather $API_KEY")
    return service.getWeather(lat, lon, apiKey = API_KEY)
  }
}