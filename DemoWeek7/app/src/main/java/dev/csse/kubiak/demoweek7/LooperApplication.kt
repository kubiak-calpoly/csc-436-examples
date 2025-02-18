package dev.csse.kubiak.demoweek7

import android.app.Application
import android.content.Context
import android.util.Log

class LooperApplication: Application() {
  // Application context is needed to create the ViewModels with the
  // ViewModelProvider.Factory
  lateinit var appContext: Context

  // For onCreate() to run, android:name=".ToDoListApplication" must
  // be added to <application> in AndroidManifest.xml
  override fun onCreate() {
    super.onCreate()
    Log.d("LooperApplication", "onCreate called")
    appContext = this.applicationContext
  }
}

