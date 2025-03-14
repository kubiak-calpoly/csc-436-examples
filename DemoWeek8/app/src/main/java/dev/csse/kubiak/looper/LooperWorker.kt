package dev.csse.kubiak.looper

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

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
