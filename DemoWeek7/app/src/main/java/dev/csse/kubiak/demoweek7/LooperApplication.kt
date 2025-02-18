package dev.csse.kubiak.demoweek7

import android.app.Application
import android.content.Context
import android.util.Log

class LooperApplication : Application() {
  lateinit var appContext: Context

  override fun onCreate() {
    super.onCreate()
    Log.d("LooperApplication", "onCreate called")
    appContext = this.applicationContext
  }
}