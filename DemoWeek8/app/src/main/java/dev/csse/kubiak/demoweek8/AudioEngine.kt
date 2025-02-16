package dev.csse.kubiak.demoweek8

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

class AudioEngine(val context: Context) {

  val click_hi = MediaPlayer.create(
    context,
    R.raw.perc_metronomequartz_hi
  )
  val click_lo = MediaPlayer.create(
    context,
    R.raw.perc_metronomequartz_lo
  )

  fun prepare(loop: Loop) {
    Log.d("AudioEngine", "Preparing to play loop")
  }

  fun playAtPosition(position: Loop.Position) {
    Log.d("AudioEngine", "playing sound(s) at position=${position}")

    if ( position.subdivision == 1 ) {
      val sound =
        if (position.beat == 1) click_hi
        else click_lo

      if (sound.isPlaying) {
        sound.stop()
        sound.prepare()
      }

      sound.start()
    }
  }
}