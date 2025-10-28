package dev.csse.kubiak.demoweek6

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay

val KEY_MILLIS_COUNT = "dev.csse.kubiak.demoweek6.KEY_MILLIS_COUNT"
val KEY_TOTAL_MILLIS = "dev.csse.kubiak.demoweek6.KEY_TOTAL_MILLIS"
val KEY_ITERATION_MILLIS = "dev.csse.kubiak.demoweek6.KEY_ITERATION_MILLIS"

class LooperWorker(context: Context, parameters: WorkerParameters) :
  CoroutineWorker(context, parameters) {

  override suspend fun doWork(): Result {
    var millisCount = inputData.getLong(KEY_MILLIS_COUNT, 0)
    var totalMillis = inputData.getLong(KEY_TOTAL_MILLIS, 0)
    var iterationMillis = inputData.getLong(KEY_ITERATION_MILLIS, 0)

    if (millisCount >= totalMillis) {
      return Result.failure()
    }

    Log.i(
      "LooperWorker",
      "Worker started loops at ${millisCount}ms"
    )

    // createLooperNotificationChannel()

    // syncronize millisCount to iteration
    val millisRemainInIteration = iterationMillis -
            millisCount % iterationMillis

    if (millisRemainInIteration > 0) {
      delay(millisRemainInIteration)
      millisCount += millisRemainInIteration
    }

    while (millisCount < totalMillis) {
      val loopCompleted = millisCount / iterationMillis

      if (loopCompleted > 0) {
        postLooperNotification(
          "Loop #$loopCompleted " +
                  "finished at ${millisCount}ms"
        )
      }
      delay(iterationMillis) // TODO: adjust for starting mid-loop
      millisCount += iterationMillis
    }

    postLooperNotification("All ${millisCount / iterationMillis} loops finished")

    return Result.success()
  }
}

private fun postLooperNotification(text: String) {
  Log.i("LooperWorker", text)
}