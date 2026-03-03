package dev.csse.kubiak.audiodemo

import android.app.Application
import dev.csse.kubiak.audiodemo.audio.AudioEngine

class MainApplication: Application() {
  lateinit var audioEngine: AudioEngine

  override fun onCreate() {
    super.onCreate()

    audioEngine = AudioEngine(applicationContext)
  }
}