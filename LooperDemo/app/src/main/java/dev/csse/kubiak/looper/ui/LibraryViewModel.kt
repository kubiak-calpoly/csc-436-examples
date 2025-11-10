package dev.csse.kubiak.looper.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.csse.kubiak.looper.LooperApplication
import dev.csse.kubiak.looper.Track
import dev.csse.kubiak.looper.Loop
import dev.csse.kubiak.looper.data.LoopEntity
import dev.csse.kubiak.looper.data.LooperRepository
import dev.csse.kubiak.looper.data.TrackEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LibraryViewModel(
  private val looperRepo: LooperRepository
) : ViewModel() {

  val selectedLoopFlow =
    MutableStateFlow<LoopEntity?>(null)
  val loopsFlow =
    looperRepo.getLoops()

  @OptIn(ExperimentalCoroutinesApi::class)
  val combinedFlow =
    selectedLoopFlow.flatMapLatest { loop: LoopEntity? ->
      val tracksFlow =
        if (loop != null)
          looperRepo.getTracks(loop)
        else
          flowOf(emptyList())
      combine(
        selectedLoopFlow,
        loopsFlow,
        tracksFlow
      ) { selectedLoop, loops, tracks ->
        LibraryScreenUiState(
          loopList = loops,
          selectedLoopId = selectedLoop?.id,
          trackList = tracks
        )
      }
    }

  val uiState: StateFlow<LibraryScreenUiState> =
    combinedFlow
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
  ) {
    looperRepo.addLoop(
      LoopEntity(
        title = title,
        barsToLoop = barsToLoop,
        beatsPerBar = beatsPerBar,
        subdivisions = subdivisions
      ),
      tracks.mapIndexed { i, track ->
        TrackEntity(
          name = track.name,
          trackNum = i,
          size = track.getSize(),
          data = track.getString(),
          sound = track.sound
        )
      }
    )
  }

  fun selectLoop(loop: LoopEntity) {
    selectedLoopFlow.value = loop
  }

  fun deselectLoop() {
    selectedLoopFlow.value = null
  }
}


data class LibraryScreenUiState(
  val loopList: List<LoopEntity> = emptyList(),
  val selectedLoopId: Long? = null,
  val trackList: List<TrackEntity> = emptyList()
)

