package dev.csse.kubiak.demoweek7.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
  // TODO: add foreign key
])
data class TrackEntity(
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0,

  // TODO: add loopId

  var trackNum: Int = 0,
  var name: String = "",
  var size: Int = 1,
  var data: String = ""

)