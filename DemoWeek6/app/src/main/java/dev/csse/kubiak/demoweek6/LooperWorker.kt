package dev.csse.kubiak.demoweek6

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
val KEY_MILLIS_COUNT = "dev.csse.kubiak.demoweek6.KEY_MILLIS_COUNT"
val KEY_TOTAL_MILLIS = "dev.csse.kubiak.demoweek6.KEY_TOTAL_MILLIS"
val KEY_ITERATION_MILLIS = "dev.csse.kubiak.demoweek6.KEY_ITERATION_MILLIS"

class LooperWorker(context: Context, parameters: WorkerParameters) :
  CoroutineWorker(context, parameters) {

  private val notificationManager: NotificationManager =
    context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager


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

    createNotificationChannel()

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
        postNotification(
          "Loop #$loopCompleted " +
                  "finished at ${millisCount}ms"
        )
      }
      delay(iterationMillis) // TODO: adjust for starting mid-loop
      millisCount += iterationMillis
    }

    postNotification("All ${millisCount / iterationMillis} loops finished")

    return Result.success()
  }


  private fun createNotificationChannel() {
    val channel = NotificationChannel(
      CHANNEL_ID_PLAYER,
      "Player Notification Channel",
      NotificationManager.IMPORTANCE_HIGH
    )
    channel.description = "Show progress of running looper"

    // Register channel
    notificationManager.createNotificationChannel(channel)
  }

  private fun postNotification(text: String) {
    val notification = NotificationCompat.Builder(
      applicationContext, CHANNEL_ID_PLAYER
    ).setSmallIcon(android.R.drawable.ic_dialog_info)
      .setContentTitle(
        applicationContext.getString(R.string.app_name)
      )
      .setContentText(text)
      .build()

    if (notificationManager.areNotificationsEnabled()) {
      notificationManager.notify(0, notification)
    }
    Log.i("LooperWorker", text)
  }

}