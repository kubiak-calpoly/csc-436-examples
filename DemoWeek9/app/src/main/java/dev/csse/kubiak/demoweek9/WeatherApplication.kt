package dev.csse.kubiak.demoweek9

import android.app.Application
import dev.csse.kubiak.demoweek9.data.WeatherRepository
import dev.csse.kubiak.demoweek9.data.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApplication : Application() {
  override fun onCreate() {
    super.onCreate()
  }
}