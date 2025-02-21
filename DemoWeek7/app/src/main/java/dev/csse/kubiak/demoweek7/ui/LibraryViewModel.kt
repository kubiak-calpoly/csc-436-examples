package dev.csse.kubiak.demoweek7.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.csse.kubiak.demoweek7.LooperApplication
import dev.csse.kubiak.demoweek7.Track
import dev.csse.kubiak.demoweek7.Loop
import dev.csse.kubiak.demoweek7.data.LoopEntity
import dev.csse.kubiak.demoweek7.data.LooperRepository
import dev.csse.kubiak.demoweek7.data.TrackEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LibraryViewModel(private val looperRepo: LooperRepository) : ViewModel() {
  val uiState: StateFlow<LibraryScreenUiState> =
    looperRepo.getLoops()
      .map {
        LibraryScreenUiState(
          loopList = it
        )
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = LibraryScreenUiState(),
      )

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as LooperApplication)
        LibraryViewModel(application.looperRepo)
      }
    }
  }

  fun addLoop(
    title: String,
    barsToLoop: Int,
    beatsPerBar: Int,
    subdivisions: Int,
    tracks: List<Track>,
  ): Long {
    return looperRepo.addLoop(
      LoopEntity(title = title,
        barsToLoop = barsToLoop,
        beatsPerBar =  beatsPerBar,
        subdivisions = subdivisions
      ),
      tracks.mapIndexed { i, track ->
        TrackEntity(
          name = track.name,
          trackNum = i,
          size = track.getSize(),
          data = track.getString()
        )
      }
    )
  }
}

data class LibraryScreenUiState(
  val loopList: List<LoopEntity> = emptyList(),
)

