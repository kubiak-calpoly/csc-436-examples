package dev.csse.kubiak.looper.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SamplerViewModel() : ViewModel() {
  var sampleFile: String? by mutableStateOf<String?>(null)


}