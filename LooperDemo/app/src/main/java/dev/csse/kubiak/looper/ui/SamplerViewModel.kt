package dev.csse.kubiak.looper.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.csse.kubiak.looper.AudioPlayer
import dev.csse.kubiak.looper.AudioRecorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SamplerViewModel() : ViewModel() {
  var sampleFile: String? by mutableStateOf<String?>(null)
  var durationMs: Int by mutableIntStateOf(1000)
  var countInMs: Int by mutableIntStateOf(0)
    private set
  var progressMs: Int by mutableIntStateOf(0)
    private set
  var isPlaying: Boolean by mutableStateOf(false)
    private set
  var isRecording: Boolean by mutableStateOf(false)
    private set

  fun sample(recorder: AudioRecorder) {
    viewModelScope.launch {
      countInMs = 3000
      while ( countInMs > 0 ) {
        delay(100)
        countInMs -= 100
      }
      Log.d("SamplerViewModel", "sampling for $durationMs ms")
      recorder.start()
      isRecording = true
      while ( progressMs < durationMs ) {
        delay(100)
        progressMs += 100
      }
      recorder.stop()
      isRecording = false
      progressMs = 0
    }
  }

  fun playback(player: AudioPlayer) {
    viewModelScope.launch {
      Log.d("SamplerViewModel", "playing for $durationMs ms")
      player.start()
      isPlaying = true
      while ( progressMs < durationMs ) {
        delay(100)
        progressMs += 100
      }
      player.stop()
      isPlaying = false
      progressMs = 0
    }
  }
}