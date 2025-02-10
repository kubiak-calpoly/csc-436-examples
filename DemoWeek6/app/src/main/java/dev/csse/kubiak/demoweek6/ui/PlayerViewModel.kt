package dev.csse.kubiak.demoweek6.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import dev.csse.kubiak.demoweek6.Loop
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.descriptors.PrimitiveKind

class PlayerViewModel : ViewModel() {
  var iterations: Int by mutableIntStateOf(8)
  var bpm: Int by mutableIntStateOf(60)

  var millisCount: Long by mutableLongStateOf(0L)
    private set

  var isRunning by mutableStateOf(false)
    private set

  private var tickingJob: Job? = null

  fun startPlayer(loop: Loop) {
    val millisPerTick = getMillisPerTick(loop)
    val totalMillis = iterations *
            loop.ticksPerIteration * millisPerTick

    if (loop.ticksPerIteration > 0) {
      isRunning = true

      tickingJob = viewModelScope.launch {
        while (millisCount < totalMillis) {
          delay(millisPerTick)
          millisCount += millisPerTick
        }
        isRunning = false
        millisCount = 0
      }
    }
  }

  fun getMillisPerTick(loop: Loop): Long {
    return 60000L / (if(bpm == 0) 60 else bpm) /
      loop.subdivisions
  }

  fun getPosition(loop: Loop): Loop.Position {
    val tickCount = millisCount / getMillisPerTick(loop)
    return loop.getPosition(tickCount.toInt())
  }

  fun pausePlayer() {
    Log.d("PlayerViewModel", "Player Paused")
    tickingJob?.cancel()
    isRunning = false
  }

  fun resetPlayer() {
    millisCount = 0
  }
}