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
  var iterations: Int by mutableIntStateOf(8)
  var bpm: Int by mutableIntStateOf(60)

  var millisCount: Long by mutableLongStateOf(0L)
    private set

  var isRunning by mutableStateOf(false)
    private set

  fun startPlayer(loop: Loop) {
    val millisPerTick = getMillisPerTick(loop)
    val totalMillis = iterations *
            loop.ticksPerIteration * millisPerTick

    if (loop.ticksPerIteration > 0) {
      isRunning = true

      viewModelScope.launch {
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

  fun getMillisPerTick(loop: Loop): Long {
    return 60000L / bpm
  }

  fun getPosition(loop: Loop): Loop.Position {
    if (isRunning || millisCount > 0) {
      val tickCount = millisCount / getMillisPerTick(loop)
      return loop.getPosition(tickCount.toInt())
    } else {
      return Loop.Position(0,0,0);
    }
  }
}