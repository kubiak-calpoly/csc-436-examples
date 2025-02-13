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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlin.times

class PlayerViewModel : ViewModel() {
  var iterations: Int by mutableIntStateOf(4)
  var bpm: Int by mutableIntStateOf(120)

  var runningLoop: Loop? = null
  var isRunning: Boolean = false
    get() { return runningLoop != null }

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

  val _positionState = MutableStateFlow(Loop.Position())
  var positionState: StateFlow<Loop.Position> =
    _positionState //.asStateFlow()

  var tickerJob: Job? = null

  val positionFlow: Flow<Loop.Position> = flow {
    if (runningLoop != null) {
      var millisCount = 0L
      Log.d("PlayerViewModel", "starting flow at $millisCount")
      while( millisCount <= totalMillis) {
        Log.d("PlayerViewModel", "emitting position at ${millisCount}ms")
        emit(getPosition(runningLoop!!, millisCount))
        delay(millisPerTick)
        millisCount += millisPerTick
      }
      runningLoop = null
      emit(Loop.Position())
    }
  }

  fun startPlayer(loop: Loop) {
    runningLoop = loop
    Log.d(
      "PlayerViewModel",
      "starting Player totalMillis=$totalMillis, millisPerTick=$millisPerTick"
    )

    tickerJob = viewModelScope.launch(Dispatchers.Default) {
      positionFlow.collect {
        _positionState.value = it
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
    tickerJob?.cancel()
  }

  fun resetPlayer() {
    //millisCount = 0
  }
}