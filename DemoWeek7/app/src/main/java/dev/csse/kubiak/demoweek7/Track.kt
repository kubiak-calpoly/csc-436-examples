package dev.csse.kubiak.demoweek7

import android.content.Context
import android.media.SoundPool
import androidx.compose.runtime.key
import androidx.compose.runtime.produceState

class Track(
  val name: String = "Unnamed Track"
) {
  var hits: MutableMap<Position, Hit> = mutableMapOf()

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
    hits: Map<Position, Hit>? = null
  ): Track {
    val track = Track(if(name == null) this.name else name)
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

