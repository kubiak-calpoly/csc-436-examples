package dev.csse.kubiak.demoweek8

import android.content.Context
import android.media.SoundPool

class Track {
  val sounds: SoundPool = SoundPool.Builder()
    .setMaxStreams(16)
    .build()
  val hits: MutableList<Hit> = mutableListOf()

  fun addSound(context: Context, resId: Int, priority: Int = 1): Int {
    return sounds.load(context, resId, priority)
  }

  fun addHit(position: Position, soundId: Int) {
    hits.add(Hit(position, soundId))
  }

  fun removeHits(position: Position, soundId: Int? = null) {
    hits.removeIf {
      it.position == position &&
              (soundId == null || soundId == it.soundId)
    }
  }

  fun playHits(position: Position) {
    hits.filter {
      it.position == position
    }.forEach {
      it.play()
    }
  }

  data class Position (
    val bar: Int = 1,
    val beat: Int = 1,
    val subdivision: Int = 1
  )

  inner class Hit(
    val position: Position,
    val soundId: Int,
    val leftVolume: Float = 1.0f,
    val rightVolume: Float = 1.0f
  ) {
    fun play() {
      sounds.play(soundId, leftVolume, rightVolume, 1, 0, 0f)
    }
  }
}

