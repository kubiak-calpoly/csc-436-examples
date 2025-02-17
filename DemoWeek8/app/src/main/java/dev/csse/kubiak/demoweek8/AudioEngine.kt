package dev.csse.kubiak.demoweek8

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.util.Log
import kotlin.io.path.fileVisitor

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

  fun prepareSoundAsset(
    path: String,
    priority: Int = 1,
    whenPrepared: (Int, String) -> Unit
  ) {
    val assetsManager = context.assets
    val alreadyLoaded = assetsLoaded[path]

    if (alreadyLoaded == null) {
      val afd = assetsManager.openFd("sounds/$path")
      sounds.setOnLoadCompleteListener {
        pool, id, status ->
        Log.i("AudioEngine",
          "Sound $id loaded (status=$status) from $path")
        assetsLoaded[path] = id
        whenPrepared(id,path)
      }
      sounds.load(afd, priority)
    } else {
       whenPrepared(alreadyLoaded, path)
    }
  }

  fun prepare(
    tracks: List<Track>,
    whenPrepared: (List<PreparedTrack>) -> Unit = {}
  ) {
    val activeTracks = tracks.filter    {
      track -> track.sound != null
    }
    val preparedTracks = activeTracks.map {
      track -> PreparedTrack(track, -1)
    }
      .toMutableList()

    Log.d("AudioEngine", "Preparing ${tracks.size} tracks")
    activeTracks
      .mapIndexed { i, track ->
        prepareSoundAsset(track.sound!!) {
          id, path ->
          Log.d("AudioEngine", "loaded asset $id for $path")
          preparedTracks[i] = PreparedTrack(track, id)
        }
      }
    // TODO: wait until prepared
    whenPrepared(preparedTracks)
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

      sounds.play(sound, volume, volume, 1, 0, 1.0f)
    }

    // all prepared tracks
    tracks.forEach { prepared ->
      val hit: Track.Hit? = prepared.track.getHit(tpos)
      if (hit != null) {
        val volume = hit.volume
        sounds.play(prepared.soundId, volume, volume, 1, 0, 1.0f)
        Log.d("AudioEngine", "playing sound ${prepared.soundId} at position=${tpos}")
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
