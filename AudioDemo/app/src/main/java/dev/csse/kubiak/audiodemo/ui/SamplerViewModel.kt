package dev.csse.kubiak.audiodemo.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.csse.kubiak.audiodemo.audio.AudioSampler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class AudioSample(
  val amplitude: Int
)

class SamplerViewModel() : ViewModel() {
  var hasPermission by mutableStateOf(false)
  private var sampler: AudioSampler? = null

  var isRunning by mutableStateOf(false)
  val latestSamples = mutableStateListOf<AudioSample>()

  @RequiresPermission(Manifest.permission.RECORD_AUDIO)
  fun requestPermission(
    context: Context,
    launcher: ManagedActivityResultLauncher<String, Boolean>
  ) {
    val permission = Manifest.permission.RECORD_AUDIO
    if (ActivityCompat.checkSelfPermission
        (
        context, permission
      ) != PERMISSION_GRANTED
    ) {
      launcher.launch(permission)
    } else {
      hasPermission = true
      initializeSampler(context)
    }
  }

  @RequiresPermission(Manifest.permission.RECORD_AUDIO)
  fun initializeSampler(
    context: Context,
    samplingRate: Int = 48_000,
    bufferSize: Int = 256
  ) {
    if (hasPermission) {
      sampler = AudioSampler(samplingRate, bufferSize)
      Log.d("RecordViewModel", "Recorder Initialized")
    }
  }

  var samplerJob: Job? = null

  fun startSampling(samplesToKeep: Int = 100 ) {
    sampler?.let { sampler ->
      val audioFlow = sampler.samplerState.map { buffer ->
        // Log.d("RecordViewModel", "Got data ${buffer.data.size}")
        if (buffer.data.isNotEmpty())
          AudioSample(buffer.data.max() - buffer.data.min())
        else
          AudioSample(0)
      }
      samplerJob = viewModelScope.launch {
        isRunning = true
        audioFlow.collect { sample ->
          latestSamples.add(sample)
          if (latestSamples.size > samplesToKeep)
            latestSamples.removeRange(
              0, latestSamples.size - samplesToKeep
            )
        }
      }
      sampler.startCapture(viewModelScope)
    }
  }

  fun pauseSampling() {
    samplerJob?.cancel()
    sampler?.stopSampling()
    isRunning = false
  }

  fun resetSampler() {
    latestSamples.clear()
  }

}