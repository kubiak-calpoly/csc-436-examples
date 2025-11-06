package dev.csse.kubiak.demoweek7.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [
    // TODO: list entities here
  ],
  version = 1
)
abstract class LooperDatabase : RoomDatabase() {
  // TODO: add Dao accessors here
}