package dev.csse.kubiak.demoweek8.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [
    LoopEntity::class,
    TrackEntity::class
  ],
  version = 1
)
abstract class LooperDatabase : RoomDatabase() {
  abstract fun trackDao(): TrackDao
  abstract fun loopDao(): LoopDao
}