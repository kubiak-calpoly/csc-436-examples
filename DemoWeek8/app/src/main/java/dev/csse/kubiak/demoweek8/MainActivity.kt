package dev.csse.kubiak.demoweek8

import android.content.pm.PackageManager
import android.Manifest
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import dev.csse.kubiak.demoweek8.ui.LooperApp
import dev.csse.kubiak.demoweek8.ui.PlayerViewModel
import dev.csse.kubiak.demoweek8.ui.theme.DemoWeek8Theme

class MainActivity : ComponentActivity() {
  private var playerViewModel: PlayerViewModel? = null
  private var audioEngine: AudioEngine? = null

  private val permissionRequestLauncher =
    registerForActivityResult(
      ActivityResultContracts.RequestPermission()
    ) { isGranted ->
      val message = if (isGranted) "Permission granted" else "Permission NOT granted"
      Log.i("MainActivity", message)
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    audioEngine = AudioEngine(applicationContext)
    if (ActivityCompat.checkSelfPermission(this,
        Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
      permissionRequestLauncher.launch(
        Manifest.permission.RECORD_AUDIO
      )
    }
    setContent {
      playerViewModel = viewModel()
      DemoWeek8Theme {
        LooperApp(
          engine = audioEngine!!,
          playerViewModel = playerViewModel!!
        )
      }
    }
  }

  override fun onStop() {
    super.onStop()

  }

}
