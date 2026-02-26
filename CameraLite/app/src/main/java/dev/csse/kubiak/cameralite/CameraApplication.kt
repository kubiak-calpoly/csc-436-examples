package dev.csse.kubiak.cameralite

import android.app.Application

class CameraApplication : Application() {
   // For onCreate() to run, android:name=".CameraApplication" must
   // be added to <application> in AndroidManifest.xml
   override fun onCreate() {
      super.onCreate()
   }
}