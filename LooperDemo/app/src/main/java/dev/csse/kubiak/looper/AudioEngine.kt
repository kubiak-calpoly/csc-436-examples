package dev.csse.kubiak.looper

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
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

  val click_hi = addSoundResource(R.raw.perc_metronomequartz_hi)
  val click_lo = addSoundResource(R.raw.perc_metronomequartz_lo)

  fun addSoundResource(resId: Int, priority: Int = 1): Int {
    return sounds.load(context, resId, priority)
  }

  private val assetsLoaded: MutableMap<String, Int> = mutableMapOf()

  fun prepareSoundAssets(
    tracks: List<Track>,
    whenPrepared: (List<PreparedTrack>) -> Unit
  ) {
    val priority: Int = 1
    val assetsManager = context.assets

    // load the sounds sequentially, because SoundPool is not threadsafe

    val preparedTracks: MutableList<PreparedTrack> = mutableListOf()

    fun prepareNextTrack(nextTrack: Int = 0) {
      if (nextTrack >= tracks.size) {
        whenPrepared(preparedTracks)
      } else {
        val track = tracks[nextTrack]
        val path = track.sound
        val alreadyLoaded = assetsLoaded[path]

        if (path != null && alreadyLoaded == null) {
          val afd = assetsManager.openFd("sounds/$path")
          sounds.setOnLoadCompleteListener { pool, id, status ->
            Log.i(
              "AudioEngine",
              "Sound $id loaded (status=$status) from $path for ${track.name}"
            )
            assetsLoaded[path] = id
            preparedTracks.add(PreparedTrack(track, id))
            prepareNextTrack(nextTrack + 1)
          }
          sounds.load(afd, priority)
        } else {
          if (alreadyLoaded != null)
            preparedTracks.add(PreparedTrack(track, alreadyLoaded))
          prepareNextTrack(nextTrack + 1)
        }
      }
    }

    prepareNextTrack()
  }

  fun prepare(
    tracks: List<Track>,
    whenAllPrepared: (List<PreparedTrack>) -> Unit = {}
  ) {
    val activeTracks = tracks.filter { track ->
      track.sound != null
    }

    prepareSoundAssets(activeTracks, whenAllPrepared)
  }

  fun playSound(sound: Int, volume: Float = 1f) {
    sounds.play(sound, volume, volume, 1, 0, 1.0f)
  }

  fun playAtPosition(
    position: Loop.Position,
    tracks: List<PreparedTrack>
  ) {
    val tpos = Track.Position(position.bar, position.beat, position.subdivision)

    // click track
    if (position.subdivision == 1) {
      val sound =
        if (position.beat == 1) click_hi
        else click_lo
      val volume =
        if (position.beat == 1) 1.0f
        else 0.5f

      playSound(sound, volume)
    }

    // all prepared tracks
    tracks.forEach { prepared ->
      val hit: Track.Hit? = prepared.track.getHit(tpos)
      if (hit != null) {
        playSound(prepared.soundId, hit.volume)
      }
    }
  }

  fun listAudioAssets(): List<String> {
    val soundDir = "sounds"
    val groups = context.assets.list(soundDir)

    if (groups == null) {
      return listOf<String>()
    } else {
      val listOfLists: List<List<String>> =
        groups.map { group ->
          val dir = "$soundDir/$group"
          val list = context.assets.list(dir)
          return list?.toList()?.map { file ->
            "$group/$file"
          } ?: listOf()
        }
      return listOfLists.flatten()
    }
  }
}

data class PreparedTrack(
  val track: Track,
  val soundId: Int
)
