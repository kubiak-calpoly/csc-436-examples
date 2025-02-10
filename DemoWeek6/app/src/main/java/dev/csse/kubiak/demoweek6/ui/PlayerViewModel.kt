package dev.csse.kubiak.demoweek6.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.csse.kubiak.demoweek6.Loop
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.descriptors.PrimitiveKind

class PlayerViewModel : ViewModel() {
  private var tickerJob: Job? = null

  var iterations: Int by mutableIntStateOf(8)
  var bpm: Int by mutableIntStateOf(60)

  var millisCount: Long by mutableLongStateOf(0L)
    private set

  fun getMillisPerTick(loop: Loop): Long {
    return 60000L / bpm / loop.subdivisions
  }

  fun getPosition(loop: Loop): Loop.Position {
    val tickCount = millisCount / getMillisPerTick(loop)
    return loop.getPosition(tickCount.toInt())
  }

  var isRunning by mutableStateOf(false)
    private set

  fun startPlayer(loop: Loop) {
    val millisPerTick = getMillisPerTick(loop)
    val totalMillis = iterations *
            loop.ticksPerIteration * millisPerTick

    if (loop.ticksPerIteration > 0) {
      isRunning = true

      tickerJob = viewModelScope.launch {
        while (isRunning && millisCount < totalMillis) {
          delay(millisPerTick)
          millisCount += millisPerTick
        }
        if ( isRunning ) {
          isRunning = false
          millisCount = 0
        }
      }
    }
  }

  fun pausePlayer() {
    isRunning = false
  }

  fun resumePlayer() {
    isRunning = true
  }

  fun resetPlayer() {
    millisCount = 0
  }
}