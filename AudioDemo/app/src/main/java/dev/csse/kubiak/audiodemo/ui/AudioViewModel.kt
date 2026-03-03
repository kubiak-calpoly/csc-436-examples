package dev.csse.kubiak.audiodemo.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AudioViewModel() : ViewModel() {
  var soundFile: String? by mutableStateOf<String?>(null)
}