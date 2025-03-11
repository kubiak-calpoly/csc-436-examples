package dev.csse.kubiak.looper

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AudioPlayer(
  val context: Context
) {

  var player: MediaPlayer? = null

  fun start(filename: String) {
    val file: File = File(context.filesDir, filename)
    MediaPlayer.create(context, file.toUri()).apply {
      player = this
      start()
    }
  }

  fun stop() {
    player?.stop()
    player?.release()
    player = null
  }
}