package dev.csse.kubiak.demoweek9.data

import android.content.Context
import android.util.Log
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.getString
import dev.csse.kubiak.demoweek9.R

class WeatherRepository(
  val context: Context,
  val service: WeatherService
) {
}