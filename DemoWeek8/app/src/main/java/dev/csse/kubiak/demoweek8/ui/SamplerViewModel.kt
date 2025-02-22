package dev.csse.kubiak.demoweek8.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.io.File

class SamplerViewModel() : ViewModel() {
  var sampleFile: String? by mutableStateOf<String?>(null)


}