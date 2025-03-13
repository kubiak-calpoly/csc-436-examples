package dev.csse.kubiak.demoweek8

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class AudioRecorder(
  val context: Context
) {
  var recorder: MediaRecorder? = null

  fun createRecorder(): MediaRecorder {
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      MediaRecorder(context)
    } else MediaRecorder()
  }

  fun start(filename: String) {
    val file: File = File(context.filesDir, filename)

    createRecorder().apply {
      setAudioSource(MediaRecorder.AudioSource.MIC)
      setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
      setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
      setOutputFile(FileOutputStream(file).fd)

      prepare()
      start()
      Log.d("AudioRecorder", "Recording started on $filename")
      recorder = this
    }
  }

  fun stop() {
    recorder?.stop()
    recorder?.reset()
    recorder = null
  }
}