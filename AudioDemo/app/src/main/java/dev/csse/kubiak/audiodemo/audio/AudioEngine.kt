package dev.csse.kubiak.audiodemo.audio

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log

class AudioEngine(
  val context: Context,
  val maxStreams: Int = 16
) {

  val audioAttributes = AudioAttributes.Builder()
    .setUsage(
      AudioAttributes.USAGE_MEDIA
    )
    .setContentType(
      AudioAttributes.CONTENT_TYPE_MUSIC
    )
    .build()

  val sounds: SoundPool = SoundPool.Builder()
    .setMaxStreams(maxStreams)
    .setAudioAttributes(audioAttributes)
    .build()


  fun addSoundResource(resId: Int, priority: Int = 1): Int {
    return sounds.load(context, resId, priority)
  }

  fun addSoundAsset(
    assetFileDescriptor: AssetFileDescriptor,
    priority: Int = 1,
    whenPrepared: (Int) -> Unit = {}): Int {
    sounds.setOnLoadCompleteListener { pool, id, status ->
      Log.i("AudioEngine", "Sound $id loaded (status=$status)")
      whenPrepared(id)
    }
    return sounds.load(assetFileDescriptor, priority)
  }


  fun playSound(sound: Int, volume: Float = 1f) {
    sounds.play(sound, volume, volume, 1, 0, 1.0f)
  }

}


