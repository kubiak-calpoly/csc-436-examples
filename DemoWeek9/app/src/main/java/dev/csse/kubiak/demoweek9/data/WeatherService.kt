package dev.csse.kubiak.demoweek9.data

import retrofit2.http.GET
import retrofit2.http.Query

const val SLO_COORD_LAT = 35.28275f
const val SLO_COORD_LON = -120.65962f

interface WeatherService {

  @GET("weather")
  suspend fun getWeather(
    @Query("lat") lat: Float? = SLO_COORD_LAT,
    @Query("lon") lon: Float? = SLO_COORD_LON,
    @Query("appid") apiKey: String
  ): WeatherReport
}

