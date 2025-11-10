package dev.csse.kubiak.looper.data

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.csse.kubiak.looper.data.LoopDao

@Database(
  entities = [
    LoopEntity::class,
    TrackEntity::class
  ],
  version = 2
)
abstract class LooperDatabase : RoomDatabase() {
  abstract fun trackDao(): TrackDao
  abstract fun loopDao(): LoopDao
}