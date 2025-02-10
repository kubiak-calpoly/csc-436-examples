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
}