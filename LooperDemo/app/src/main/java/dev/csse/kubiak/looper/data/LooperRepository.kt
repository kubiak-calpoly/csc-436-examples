package dev.csse.kubiak.looper.data

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

  private val trackDao = database.trackDao()
  private val loopDao = database.loopDao()

  fun getLoops() = loopDao.getLoops()
  fun getTracks(loop: LoopEntity) =
    trackDao.getTracks(loop.id)

  fun addLoop(
    loop: LoopEntity,
    tracks: List<TrackEntity>
  ) {
    if (loop.title.trim() != "") {
      CoroutineScope(Dispatchers.IO).launch {
        loop.id = loopDao.addLoop(loop)
        tracks.forEach { track ->
          track.loopId = loop.id
          trackDao.addTrack(track)
        }
      }
    }
  }

}