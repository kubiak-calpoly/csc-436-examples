package dev.csse.kubiak.demoweek8.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.csse.kubiak.demoweek8.Loop

class LooperViewModel : ViewModel() {
  var loop: Loop by mutableStateOf(Loop())
}

