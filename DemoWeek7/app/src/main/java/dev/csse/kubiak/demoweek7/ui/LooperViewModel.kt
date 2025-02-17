package dev.csse.kubiak.demoweek7.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.csse.kubiak.demoweek7.Loop
import dev.csse.kubiak.demoweek7.Track

class LooperViewModel : ViewModel() {
  var loop: Loop by mutableStateOf(Loop())

  init {
    Log.d("LooperViewModel", "New viewmodel created")
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

