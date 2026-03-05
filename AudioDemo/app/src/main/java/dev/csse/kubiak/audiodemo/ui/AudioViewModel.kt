package dev.csse.kubiak.audiodemo.ui

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.lifecycle.ViewModel
import java.io.File

class AudioViewModel() : ViewModel() {
  var hasPermission by mutableStateOf(false)
  var soundFile: String? by mutableStateOf<String?>(null)

  fun requestPermission(
    context: Context,
    launcher: ManagedActivityResultLauncher<String, Boolean>
  ) {
    val permission = Manifest.permission.RECORD_AUDIO
    if (ActivityCompat.checkSelfPermission
        (
        context, permission
      ) != PERMISSION_GRANTED
    ) {
      launcher.launch(permission)
    } else {
      hasPermission = true
    }
  }

  @RequiresApi(Build.VERSION_CODES.S)
  private fun getSoundsDir(context: Context): File? {
    return context.getExternalFilesDir(Environment.DIRECTORY_RECORDINGS)
  }

  @RequiresApi(Build.VERSION_CODES.S)
  fun listSounds(context: Context):List<String> {
    return getSoundsDir(context)?.list()?.toList() ?: emptyList()
  }

  @RequiresApi(Build.VERSION_CODES.S)
  fun getSoundFile(context: Context, name: String): File? {
    return getSoundsDir(context)?.let { File(it, name) }
  }
}