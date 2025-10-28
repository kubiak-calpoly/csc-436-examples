package dev.csse.kubiak.demoweek6

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import dev.csse.kubiak.demoweek6.ui.LooperApp
import dev.csse.kubiak.demoweek6.ui.PlayerViewModel
import dev.csse.kubiak.demoweek6.ui.theme.DemoWeek6Theme

class MainActivity : ComponentActivity() {

  private var playerViewModel: PlayerViewModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      playerViewModel = viewModel()

      DemoWeek6Theme {
        LooperApp(playerViewModel = playerViewModel!!)
      }
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
      startWorker(playerViewModel!!)
    }
  }


  private fun startWorker(playerViewModel: PlayerViewModel) {
    val looperWorkRequest: WorkRequest =
      OneTimeWorkRequestBuilder<LooperWorker>()
        .setInputData(
          workDataOf(
            KEY_MILLIS_COUNT to playerViewModel.millisCount,
            KEY_TOTAL_MILLIS to playerViewModel.totalMillis,
            KEY_ITERATION_MILLIS to playerViewModel.millisPerIteration
          )
        ).build()

    WorkManager.getInstance(applicationContext)
      .enqueue(looperWorkRequest)
  }
}
