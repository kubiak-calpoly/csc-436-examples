package dev.csse.kubiak.demoweek7

import android.content.pm.PackageManager
import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import dev.csse.kubiak.demoweek7.ui.LooperApp
import dev.csse.kubiak.demoweek7.ui.PlayerViewModel
import dev.csse.kubiak.demoweek7.ui.theme.DemoWeek7Theme

class MainActivity : ComponentActivity() {

  // private val playerViewModel = PlayerViewModel()
  private var playerViewModel: PlayerViewModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      playerViewModel = viewModel()
      DemoWeek7Theme {
        LooperApp(playerViewModel = playerViewModel!!)
      }
    }
    // Only need permision to post notifications on Tiramisu and above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
      ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.POST_NOTIFICATIONS
      ) == PackageManager.PERMISSION_DENIED
    ) {
      permissionRequestLauncher.launch(
        Manifest.permission.POST_NOTIFICATIONS
      )
    }
  }

  override fun onStop() {
    super.onStop()
    val isRunning = playerViewModel?.isRunning ?: false

    Log.d(
      "Looper",
      "App has stopped, player is ${if (!isRunning) "NOT " else ""} running"
    )

    if (isRunning) {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
        ActivityCompat.checkSelfPermission(
          this,
          Manifest.permission.POST_NOTIFICATIONS
        ) ==
        PackageManager.PERMISSION_GRANTED
      ) {
        startWorker(playerViewModel!!)
      }
    }
  }

  private fun startWorker(playerViewModel: PlayerViewModel) {
    val looperWorkRequest: WorkRequest =
      OneTimeWorkRequestBuilder<LooperWorker>()
        .setInputData(
          workDataOf(
            // KEY_MILLIS_COUNT to playerViewModel.millisCount,
            KEY_TOTAL_MILLIS to playerViewModel.totalMillis,
            // KEY_ITERATION_MILLIS to playerViewModel.millisPerIteration
          )
        ).build()

    WorkManager.getInstance(applicationContext)
      .enqueue(looperWorkRequest)
  }

  private val permissionRequestLauncher =
    registerForActivityResult(
      ActivityResultContracts.RequestPermission()
    ) { isGranted ->
      val message =
        if (isGranted) "Permission granted"
        else "Permission NOT granted"

      Log.i("Looper", message)
    }
}
