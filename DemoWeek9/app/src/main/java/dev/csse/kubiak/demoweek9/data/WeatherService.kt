package dev.csse.kubiak.demoweek9.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

  suspend fun getWeather(
    lat: Float,
    lon: Float,
    apiKey: String
  ): WeatherReport

}

