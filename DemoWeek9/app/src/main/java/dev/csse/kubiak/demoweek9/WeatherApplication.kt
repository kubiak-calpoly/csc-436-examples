package dev.csse.kubiak.demoweek9

import android.app.Application
import dev.csse.kubiak.demoweek9.data.WeatherRepository
import dev.csse.kubiak.demoweek9.data.WeatherService
import dev.csse.kubiak.demoweek9.sensor.Accelerometer
import dev.csse.kubiak.demoweek9.sensor.Magnetometer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApplication : Application() {
  lateinit var accelerometerSensor: Accelerometer
  lateinit var magnetometerSensor: Magnetometer
  lateinit var weatherRepository: WeatherRepository

  override fun onCreate() {
    super.onCreate()

    accelerometerSensor = Accelerometer(this)
    magnetometerSensor = Magnetometer(this)

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