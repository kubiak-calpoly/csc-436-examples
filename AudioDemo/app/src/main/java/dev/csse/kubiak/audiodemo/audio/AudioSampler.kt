package dev.csse.kubiak.audiodemo.audio

import android.Manifest
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Process
import android.util.Log
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.math.max

data class SampleBuffer(
  val data: ShortArray = shortArrayOf(),
  val size: Int = 0,
  val samplingRate: Int = 48_000
)

class AudioSampler
@RequiresPermission(Manifest.permission.RECORD_AUDIO)
constructor(
  val samplingRate: Int = 48_000,
  val bufferSize: Int = 256
) {
  var isRecording = false
  val bufferSizeBytes = max(
    bufferSize,
    AudioRecord.getMinBufferSize(
      samplingRate,
      AudioFormat.CHANNEL_IN_MONO,
      AudioFormat.ENCODING_PCM_16BIT
    )
  )
  val bufferSizeShorts = bufferSizeBytes / Short.SIZE_BYTES
  val readBuffer = ShortArray(bufferSizeShorts)

  private var recorder: AudioRecord? = null
  private var recordingJob: Job? = null

  private val mutableFlow = MutableSharedFlow<SampleBuffer>()
  val samplerState = mutableFlow.asSharedFlow()

  init {
    initializeRecorder()
  }

  @RequiresPermission(Manifest.permission.RECORD_AUDIO)
  fun initializeRecorder() {
    recorder = AudioRecord(
      MediaRecorder.AudioSource.MIC,
      samplingRate,
      AudioFormat.CHANNEL_IN_MONO,
      AudioFormat.ENCODING_PCM_16BIT,
      bufferSizeBytes
    )

    if (recorder?.state != AudioRecord.STATE_INITIALIZED) {
      Log.e("AudioRecord", "initialization failed")
      recorder = null
    }
  }

  fun startCapture(scope: CoroutineScope) {
    isRecording = true
    recordingJob = scope.launch(Dispatchers.Default) {
      Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO)
      recorder?.let {
        it.startRecording()
        while (isRecording) {
          val readSize = it.read(readBuffer, 0, bufferSizeShorts)
          // Log.d("AudioSampler", "Recording $readSize samples")
          if (readSize > 0) {
            mutableFlow.emit(
              SampleBuffer(
                readBuffer,
                readSize,
                samplingRate
              )
            )
          } else break
        }
        it.stop()
        isRecording = false
      }
    }
  }

  fun stopSampling() {
    recorder?.stop()
    isRecording = false
    recordingJob?.cancel()
    recordingJob = null
  }
}