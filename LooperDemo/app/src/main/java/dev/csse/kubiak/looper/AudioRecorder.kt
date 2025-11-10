package dev.csse.kubiak.looper

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.FileDescriptor
import java.io.FileOutputStream

class AudioRecorder(
  val context: Context
) {
  var recorder: MediaRecorder? = null
  var outputStream: FileOutputStream? = null

  fun createRecorder(): MediaRecorder {
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      MediaRecorder(context)
    } else MediaRecorder()
  }

  fun prepareRecorder(filename: String) {
    val file: File = File(context.filesDir, filename)
    outputStream = FileOutputStream(file)

    createRecorder().apply {
      setAudioSource(MediaRecorder.AudioSource.MIC)
      setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
      setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
      setOutputFile(outputStream?.fd)

      prepare()
      Log.d("AudioRecorder", "Ready to record on $filename")
      recorder = this
    }
  }

  fun start() {
    Log.d("AudioRecorder", "Starting to record")
    recorder?.start()
  }

  fun stop() {
    Log.d("AudioRecorder", "Stopping recording")
    recorder?.stop()
    outputStream?.close()
    recorder?.release()
    recorder = null
  }
}