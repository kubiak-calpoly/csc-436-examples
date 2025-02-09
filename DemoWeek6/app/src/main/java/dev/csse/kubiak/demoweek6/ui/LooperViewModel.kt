package dev.csse.kubiak.demoweek6.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.csse.kubiak.demoweek6.Loop

class LooperViewModel : ViewModel() {
  var loop: Loop by mutableStateOf(Loop())
}

