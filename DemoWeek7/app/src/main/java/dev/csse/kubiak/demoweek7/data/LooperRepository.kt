package dev.csse.kubiak.demoweek7.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    "study.db"
  )
    .addCallback(databaseCallback)
    .build()

  private val trackDao = database.trackDao()
  private val loopDao = database.loopDao()

  fun getLoops() = loopDao.getLoops()

  fun addLoop(loop: LoopEntity) {
    if (loop.title.trim() != "") {
      CoroutineScope(Dispatchers.IO).launch {
        loop.id = loopDao.addLoop(loop)
      }
    }
  }
}