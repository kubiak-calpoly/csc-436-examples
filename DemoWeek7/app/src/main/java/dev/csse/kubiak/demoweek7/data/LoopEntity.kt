package dev.csse.kubiak.demoweek7.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoopEntity (
  // TODO: add PrimaryKey id

  // TODO: add creationTime Column

  var title: String = "",
  val barsToLoop: Int = 1,
  val beatsPerBar: Int = 4,
  val subdivisions: Int = 2
)