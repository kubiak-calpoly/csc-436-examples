package dev.csse.kubiak.demoweek8.ui

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
import dev.csse.kubiak.demoweek8.Loop
import dev.csse.kubiak.demoweek8.LooperApplication
import dev.csse.kubiak.demoweek8.Track
import dev.csse.kubiak.demoweek8.data.AppStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader

class LooperViewModel(
  prefStorage: AppStorage
) : ViewModel() {
  var loop: Loop by mutableStateOf(Loop())
  val tracks = mutableStateListOf<Track>()

  init {
    viewModelScope.launch {
      prefStorage.appPreferencesFlow.collect {
        loop = Loop(
          barsToLoop = it.loopBars,
          beatsPerBar = it.loopBeats,
          subdivisions = it.loopDivisions
        )
        Log.d("LooperViewModel", "setloop from preferences: $loop")
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
    Log.d("LooperViewModel", "Replaced Track $index")
    Log.d("LooperViewModel", "Replaced with $replacement / ${replacement.sound}")
    val sounds = tracks.map { t -> t.sound }
    Log.d("LooperViewModel", "Sounds now: $sounds")
  }

  fun loadTracksFromFile(context: Context, filename: String) {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val inputStream = context.openFileInput(filename)
        val reader = inputStream.bufferedReader()
        parseTracksInput(reader)
        inputStream.close()
      } catch(e: Exception) {
        // TODO: Raise an alert
        Log.e("LooperViewModel", "Error reading tracks file '$filename': $e")
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
          val bars = matchResult.groups[2]?.value
          if (bars != null) track.parseString(bars)
        }
      }
    }
  }

}


