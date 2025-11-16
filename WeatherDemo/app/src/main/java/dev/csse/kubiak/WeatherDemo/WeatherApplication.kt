package dev.csse.kubiak.WeatherDemo

import android.app.Application
import dev.csse.kubiak.WeatherDemo.data.WeatherRepository
import dev.csse.kubiak.WeatherDemo.data.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApplication : Application() {
  lateinit var weatherRepository: WeatherRepository

  override fun onCreate() {
    super.onCreate()

    val weatherService: WeatherService by lazy {
      val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .build()
      retrofit.create(WeatherService::class.java)
    }

    weatherRepository = WeatherRepository(
      applicationContext,
      weatherService
    )

  }
}