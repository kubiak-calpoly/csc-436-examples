package dev.csse.kubiak.demoweek7.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.csse.kubiak.demoweek7.AppStorage
import dev.csse.kubiak.demoweek7.Loop
import dev.csse.kubiak.demoweek7.LooperApplication
import dev.csse.kubiak.demoweek7.Track
import kotlinx.coroutines.launch

class LooperViewModel(
  prefStorage: AppStorage
) : ViewModel() {
  var loop: Loop by mutableStateOf(Loop())

  init {
    Log.d("LooperViewModel", "New viewmodel created")
    // Get app settings
    viewModelScope.launch {
      prefStorage.appPreferencesFlow.collect {
        loop = Loop(
          barsToLoop = it.loopBars,
          beatsPerBar = it.loopBeats,
          subdivisions = it.loopDivisions
        )
      }
    }
  }

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application =
          (this[
            ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY
          ] as LooperApplication)
        LooperViewModel(
          AppStorage(application.appContext)
        )
      }
    }
  }
//
//  val tracks = mutableStateListOf<Track>()
//
//  init {
//    addTrack("Kick")
//      .addHit(1)
//      .addHit(3)
//    addTrack("Snare")
//      .addHit(2)
//      .addHit(4)
//      .addHit(4,2)
//  }
//
//  fun addTrack(name: String = "Track"): Track {
//    val newTrack = Track(name)
//    tracks.add(0, newTrack)
//    return newTrack
//  }
//
//  fun updateTrack(
//    index: Int,
//    doUpdate: (Track) -> Track = { it }
//  ) {
//    val original = tracks[index]
//    val replacement = doUpdate(original)
//    tracks[index] = replacement
//  }

}

