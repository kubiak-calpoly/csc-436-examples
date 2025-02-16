package dev.csse.kubiak.demoweek8

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.util.Log

class AudioEngine(val context: Context) {

  val audioAttributes = AudioAttributes.Builder()
    .setUsage(
      AudioAttributes.USAGE_MEDIA
    )
    .setContentType(
      AudioAttributes.CONTENT_TYPE_MUSIC
    )
    .build()

  val sounds: SoundPool = SoundPool.Builder()
    .setMaxStreams(16)
    .setAudioAttributes(audioAttributes)
    .build()

  val click_hi = addSound(R.raw.perc_metronomequartz_hi )
  val click_lo = addSound(R.raw.perc_metronomequartz_lo )

  fun addSound(resId: Int, priority: Int = 1): Int {
    return sounds.load(context, resId, priority)
  }

  fun prepare(loop: Loop) {
    Log.d("AudioEngine", "Preparing to play loop")
  }

  fun playAtPosition(position: Loop.Position) {
    Log.d("AudioEngine", "playing sound(s) at position=${position}")

    if (position.subdivision == 1) {
      val sound =
        if (position.beat == 1) click_hi
        else click_lo
      val volume =
        if (position.beat == 1) 1.0f
        else 0.5f

      sounds.play(sound, volume, volume, 1, 0, 1.0f)
    }
  }
}