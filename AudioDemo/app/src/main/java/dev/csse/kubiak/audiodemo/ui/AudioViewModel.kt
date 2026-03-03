package dev.csse.kubiak.audiodemo.ui

import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.io.File

class AudioViewModel() : ViewModel() {
  var soundFile: String? by mutableStateOf<String?>(null)

  @RequiresApi(Build.VERSION_CODES.S)
  private fun getSoundsDir(context: Context): File? {
    return context.getExternalFilesDir(Environment.DIRECTORY_RECORDINGS)
  }

  @RequiresApi(Build.VERSION_CODES.S)
  fun listSounds(context: Context):List<String> {
    return (getSoundsDir(context)?.list() ?: emptyList<String>())
            as List<String>
  }

  @RequiresApi(Build.VERSION_CODES.S)
  fun getSoundFile(context: Context, name: String): File? {
    return getSoundsDir(context)?.let { File(it, name) }
  }
}