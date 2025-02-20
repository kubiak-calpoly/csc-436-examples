package dev.csse.kubiak.demoweek7.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
  ForeignKey(entity = Loop::class,
    parentColumns = ["id"],
    childColumns = ["loop_id"],
    onDelete = ForeignKey.CASCADE)
])
data class Track(
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0,

  @ColumnInfo(name = "loop_id")
  var loopId: Long = 0L,

  var trackNum: Int = 0,
  var size: Int = 1,
  var data: String = ""

)