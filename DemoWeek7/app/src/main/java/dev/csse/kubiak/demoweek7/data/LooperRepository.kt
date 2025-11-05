package dev.csse.kubiak.demoweek7.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LooperRepository(context: Context) {
  private val databaseCallback = object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
      super.onCreate(db)
    }
  }

  private val database: LooperDatabase = Room.databaseBuilder(
    context,
    LooperDatabase::class.java,
    "looper.db"
  )
    .addCallback(databaseCallback)
    .build()

  // TODO: private val trackDao =
  // TODO: private val loopDao =


  // TODO: fun getLoops() =
  // TODO: fun getTracks(loop: LoopEntity) =

  fun addLoop(
    loop: LoopEntity,
    tracks: List<TrackEntity>
  ) {
    if (loop.title.trim() != "") {
      // Launch coroutine to add tracks and loop
    }
  }

}