package dev.csse.kubiak.looper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
  ForeignKey(entity = LoopEntity::class,
    parentColumns = ["id"],
    childColumns = ["loop_id"],
    onDelete = ForeignKey.CASCADE)
])
data class TrackEntity(
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0,

  @ColumnInfo(name = "loop_id")
  var loopId: Long = 0L,

  var trackNum: Int = 0,
  var name: String = "",
  var size: Int = 1,
  var data: String = "",
  var sound: String? = null
)