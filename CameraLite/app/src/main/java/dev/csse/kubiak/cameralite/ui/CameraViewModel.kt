package dev.csse.kubiak.cameralite.ui

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.core.net.toUri

class CameraViewModel() : ViewModel() {

  private val emptyImageUri = "file://dev/null".toUri()
  var imageUri by mutableStateOf(
    emptyImageUri
  )

  val isImageEmpty get() = imageUri == emptyImageUri
}


