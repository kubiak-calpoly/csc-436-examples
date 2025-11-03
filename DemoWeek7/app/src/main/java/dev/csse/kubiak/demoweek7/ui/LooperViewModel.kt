package dev.csse.kubiak.demoweek7.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader

class LooperViewModel(
  prefStorage: AppStorage
) : ViewModel() {
  var loop: Loop by mutableStateOf(Loop())
  val tracks = mutableStateListOf<Track>()

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

  fun parseTracksInput(reader: BufferedReader) {
    val pattern = "^(\\w+):\\s*([|](([.*]*)(:[.*]*)*[|])+)".toRegex()

    reader.forEachLine { line: String ->
      // each line is a track, in the following format:
      //   Name: |..:..:..:..|..:..:..:..|
      // replace any . with a * to indicate a hit, e.g.:
      //   Kick: |*.:..:*.:..|*.:.*:*.:..|

      val matchResult = pattern.find(line)
      if (matchResult != null) {
        val name = matchResult.groups[1]?.value
        if (name != null) {
          val track = addTrack(name)
          val bars = matchResult.groups[2]?.value?.drop(1)?.dropLast(1)
          val beats = bars?.split("|")?.map { bar ->
            bar.split(":").map { m ->
              m.map { c -> if (c == '.') 0 else 1 }
            }
          }
          beats?.forEachIndexed { i, bar ->
            bar.forEachIndexed { j, beat ->
              beat.forEachIndexed { k, hit ->
                if (hit > 0) {
                  track.addHit(bar = i + 1, beat = j + 1, subdivision = k + 1)
                }
              }
            }
          }
        }
      }
    }
  }

  fun addTrack(name: String = "Track"): Track {
    val newTrack = Track(name)
    tracks.add(0, newTrack)
    return newTrack
  }

  fun updateTrack(
    index: Int,
    doUpdate: (Track) -> Track = { it }
  ) {
    val original = tracks[index]
    val replacement = doUpdate(original)
    tracks[index] = replacement
  }

}

