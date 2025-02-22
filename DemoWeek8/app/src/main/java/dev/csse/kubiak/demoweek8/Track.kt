package dev.csse.kubiak.demoweek8

import android.content.Context
import android.media.SoundPool
import androidx.compose.runtime.key

class Track(
  val name: String = "Unnamed Track"
) {

  var hits: MutableMap<Position, Hit> = mutableMapOf()
  var sound: String? = null
  var soundId: Int? = null

  data class Position(
    val bar: Int = 1,
    val beat: Int = 1,
    val subdivision: Int = 1
  )

  data class Hit(
    val volume: Float = 1.0f
  )

  fun copy(
    name: String? = null,
    sound: String? = null,
    hits: Map<Position, Hit>? = null
  ): Track {
    val track = Track(if(name == null) this.name else name)
    if(sound != null) {
      track.sound = sound
      track.soundId = null
    }
    if(hits != null) track.hits = hits.toMutableMap()
    return track
  }

  fun addHit(
    position: Position,
    volume: Float = 1.0f
  ) : Track {
    hits.set(key = position, value = Hit(volume))
    return this
  }

  fun addHit(
    beat: Int,
    subdivision: Int = 1,
    bar: Int = 1,
    volume: Float = 1.0f
  ): Track {
    val position = Position(bar, beat, subdivision)
    addHit(position, volume)
    return this
  }

  fun removeHit(position: Position) {
    hits.remove(key = position)
  }

  fun getHit(position: Position): Hit? {
    return hits[position]
  }

  fun setSound(sound: String): Track {
    this.sound = sound
    return this
  }

  fun getSize(): Int {
    return hits.keys
      .map { pos -> pos.bar }
      .max()
  }

  fun getString(): String {
    val barCount = getSize()
    val maxBeatCount = hits.keys
      .map { pos -> pos.beat }
      .max()
    val maxSubdivisionCount = hits.keys
      .map { pos -> pos.subdivision }
      .max()

    return (1..barCount).map { bar ->
      (1..maxBeatCount).map { beat ->
        (1..maxSubdivisionCount).map { subdivision ->
          val pos = Position(bar, beat, subdivision)
          val hit = getHit(pos)
          if (hit == null) "." else "*"
        } .joinToString("")
      } .joinToString(":")
    } .joinToString("|", "|", "|")
  }
}

