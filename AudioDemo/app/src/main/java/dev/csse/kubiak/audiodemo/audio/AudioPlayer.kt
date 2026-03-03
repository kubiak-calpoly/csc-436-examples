package dev.csse.kubiak.audiodemo.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AudioPlayer(
  val context: Context
) {

  var player: MediaPlayer? = null

  fun start(file: File) {
    if (file.isFile() && file.length() > 0) {
      player = MediaPlayer.create(context, file.toUri())
      player?.start()
    }
  }

  fun stop() {
    player?.stop()
    player?.release()
    player = null
  }
}