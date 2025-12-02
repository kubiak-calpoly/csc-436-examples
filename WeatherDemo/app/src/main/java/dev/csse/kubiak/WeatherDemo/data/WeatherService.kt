package dev.csse.kubiak.WeatherDemo.data

import retrofit2.http.GET
import retrofit2.http.Query



interface WeatherService {

  @GET("weather?units=imperial")
  suspend fun getWeather(
    @Query("lat") lat: Float,
    @Query("lon") lon: Float,
    @Query("appid") apiKey: String
  ): WeatherReport
}

