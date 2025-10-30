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
import dev.csse.kubiak.demoweek6.Loop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.serialization.descriptors.PrimitiveKind

class PlayerViewModel : ViewModel() {
  var iterations: Int by mutableIntStateOf(8)
  var bpm: Int by mutableIntStateOf(60)
  var totalMillis: Long by mutableLongStateOf(0L)
    private set

  var isRunning by mutableStateOf(false)
    private set
  var positionFlow: Flow<Loop.Position> = flow {}
  private var millisCount = 0L

  fun startPlayer(loop: Loop) {
    val millisPerTick = getMillisPerTick(loop)
    val millisPerIteration = loop.ticksPerIteration * millisPerTick
    totalMillis = iterations * millisPerIteration

    if (loop.ticksPerIteration > 0) {
      isRunning = true
      positionFlow = flow {
        do {
          val position = getPosition(loop, millisCount)
          emit(position)
          Log.d("PlayerViewModel", "Position at $millisCount ms: $position")
          millisCount += millisPerTick
          delay(millisPerTick)
        } while (isRunning && millisCount <= totalMillis)
        isRunning = false
      }
    }
  }

  fun pausePlayer() {
    Log.d("PlayerViewModel", "Player Paused")
    isRunning = false
  }

  fun resetPlayer() {
    millisCount = 0L
  }

  fun getMillisPerTick(loop: Loop): Long {
    return 60000L / bpm
  }

  fun getPosition(loop: Loop, millisCount: Long): Loop.Position {
    if (isRunning || millisCount > 0) {
      val tickCount = millisCount / getMillisPerTick(loop)
      return loop.getPosition(tickCount.toInt())
    } else {
      return Loop.Position(0,0,0);
    }
  }
}