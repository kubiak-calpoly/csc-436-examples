package dev.csse.kubiak.demoweek7.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [
    LoopEntity::class,
    TrackEntity::class
  ],
  version = 1,
  autoMigrations = [
  ]
)
abstract class LooperDatabase : RoomDatabase() {
  abstract fun trackDao(): TrackDao
  abstract fun loopDao(): LoopDao
}