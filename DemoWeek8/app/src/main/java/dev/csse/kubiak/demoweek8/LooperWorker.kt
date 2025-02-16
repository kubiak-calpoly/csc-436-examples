package dev.csse.kubiak.demoweek8

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay

val CHANNEL_ID_PLAYER = "channel_player"

class LooperWorker(context: Context,
                   parameters: WorkerParameters) :
  CoroutineWorker(context, parameters) {

  override suspend fun doWork(): Result {
    Log.i(
      "LooperWorker",
      "Worker started looping"
    )

    return Result.success()
  }
}
