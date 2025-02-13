package dev.csse.kubiak.demoweek6.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
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
  var iterations: Int by mutableIntStateOf(4)
  var bpm: Int by mutableIntStateOf(120)

  var isRunning by mutableStateOf(false)
    private set
  var totalMillis by mutableLongStateOf(0L)
    private set

  var positionFlow: Flow<Loop.Position> = flow { }

  private var tickingJob: Job? = null

  fun startPlayer(loop: Loop) {
    var millisCount = 0L
    val millisPerTick = getMillisPerTick(loop)
    val millisPerIteration = loop.ticksPerIteration * millisPerTick
    totalMillis = iterations * millisPerIteration

    Log.d("PlayerViewModel", "starting Player totalMillis=$totalMillis, millisPerTick=$millisPerTick")

    if (totalMillis > 0) {
      isRunning = true

      positionFlow = flow {
        Log.d("PlayerViewModel", "starting flow at $millisCount")
        while( millisCount < totalMillis) {
          Log.d("PlayerViewModel", "emitting position at ${millisCount}ms")
          emit(getPosition(loop, millisCount))
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

  fun getPosition(loop: Loop, millisCount: Long): Loop.Position {
    val tickCount = millisCount / getMillisPerTick(loop)
    return loop.getPosition(tickCount.toInt())
  }

  fun pausePlayer() {
    Log.d("PlayerViewModel", "Player Paused")
    tickingJob?.cancel()
    isRunning = false
  }

  fun resetPlayer() {
    //millisCount = 0
  }
}