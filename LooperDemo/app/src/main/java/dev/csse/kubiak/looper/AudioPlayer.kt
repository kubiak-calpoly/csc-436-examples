package dev.csse.kubiak.looper

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.core.net.toUri
import java.io.File

class AudioPlayer(
  val context: Context
) {

  var player: MediaPlayer? = null

  fun preparePlayer(filename: String) {
    val file: File = File(context.filesDir, filename)
    player = MediaPlayer.create(context, file.toUri())
  }

  fun start() {
    Log.d("AudioPlayer", "Starting to play")
    player?.start()
  }

  fun stop() {
    player?.stop()
    player?.release()
    player = null
  }
}