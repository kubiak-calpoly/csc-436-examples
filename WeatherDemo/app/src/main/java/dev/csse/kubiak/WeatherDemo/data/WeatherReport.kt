package dev.csse.kubiak.WeatherDemo.data

import com.google.gson.annotations.SerializedName

data class WeatherReport(
  val coord: Location?,
  val name: String?,
  val weather: Array<Weather>?,
  val main: Temperature?,
  val wind: Wind?
) {

  data class Location(
    val lat: Float,
    val lon: Float
  )

  data class Weather(
    val main: String,
    val description: String,
    val icon: String
  )

  data class Temperature(
    val temp: Float?,

    @SerializedName(value = "feels_like")
    val feelsLike: Float?,

    @SerializedName(value = "temp_max")
    val dailyHigh: Float?,

    @SerializedName(value = "temp_min")
    val dailyLow: Float?,
  )

  data class Wind(
    val speed: Float?,
    val deg: Float?
  )

}