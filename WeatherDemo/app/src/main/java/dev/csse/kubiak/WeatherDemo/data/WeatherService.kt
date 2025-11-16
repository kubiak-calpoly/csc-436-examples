package dev.csse.kubiak.WeatherDemo.data

import retrofit2.http.GET
import retrofit2.http.Query



interface WeatherService {

  // TODO: @GET
  suspend fun getWeather(
    // TODO: @Query for each query param
    lat: Float,
    lon: Float,
    apiKey: String
  ): WeatherReport
}

