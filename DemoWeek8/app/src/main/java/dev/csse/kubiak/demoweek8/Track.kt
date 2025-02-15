package dev.csse.kubiak.demoweek8

import android.content.Context
import android.media.SoundPool

class Track {
  val sounds: SoundPool = SoundPool.Builder()
    .setMaxStreams(16)
    .build()

  fun addSound(context: Context, resId: Int, priority: Int = 1): Int{
    return sounds.load(context, resId, priority)
  }
}