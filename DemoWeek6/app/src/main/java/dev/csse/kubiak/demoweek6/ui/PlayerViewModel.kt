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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.descriptors.PrimitiveKind

class PlayerViewModel : ViewModel() {
  var iterations: Int by mutableIntStateOf(8)
  var bpm: Int by mutableIntStateOf(60)
  var runningLoop: Loop? = null
  val millisPerTick: Long
    get() {
      return if ( runningLoop != null ) getMillisPerTick(runningLoop!!)
      else 0L
    }
  val totalMillis: Long
    get() {
      val millisPerIteration =
        (runningLoop?.ticksPerIteration?.toLong() ?: 0L) * millisPerTick
      return iterations * millisPerIteration
    }

  var isRunning by mutableStateOf(false)
    private set

  private var millisCount = 0L

  val _positionState = MutableStateFlow(Loop.Position())
  var positionState: StateFlow<Loop.Position> =
    _positionState.asStateFlow()

  val positionFlow: Flow<Loop.Position> = flow {
    if (runningLoop != null) {
      millisCount = 0L
      isRunning = true
      Log.d("PlayerViewModel", "starting flow at $millisCount")
      while( millisCount <= totalMillis) {
        Log.d("PlayerViewModel", "emitting position at ${millisCount}ms")
        emit(getPosition(runningLoop!!, millisCount))
        delay(millisPerTick)
        millisCount += millisPerTick
      }
      isRunning = false
      emit(Loop.Position())
    }
  }

  private var tickingJob: Job? = null

  fun startPlayer(loop: Loop) {
    runningLoop = loop
    tickingJob = viewModelScope.launch(Dispatchers.Default) {
      positionFlow.collect {
        _positionState.value = it
      }
    }
  }

  fun pausePlayer() {
    Log.d("PlayerViewModel", "Player Paused")
    tickingJob?.cancel()
    isRunning = false
  }

  fun resetPlayer() {
    millisCount = 0L
    _positionState.update { Loop.Position() }
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