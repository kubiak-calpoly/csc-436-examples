package dev.csse.kubiak.demoweek7

import android.app.Application
import android.content.Context
import android.util.Log
import dev.csse.kubiak.demoweek7.data.LooperRepository

class LooperApplication : Application() {
  lateinit var appContext: Context
  lateinit var looperRepo: LooperRepository

  override fun onCreate() {
    super.onCreate()
    Log.d("LooperApplication", "onCreate called")
    appContext = this.applicationContext
    looperRepo = LooperRepository(appContext)
  }
}