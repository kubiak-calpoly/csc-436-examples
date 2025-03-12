package dev.csse.kubiak.demoweek8.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoopEntity (
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0,

  var title: String = "",

  @ColumnInfo(name = "created")
  var creationTime: Long = System.currentTimeMillis(),

  val barsToLoop: Int = 1,
  val beatsPerBar: Int = 4,
  val subdivisions: Int = 2
)