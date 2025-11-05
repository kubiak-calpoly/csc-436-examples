package dev.csse.kubiak.demoweek7.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
  // TODO: add @Query
  fun getTrack(id: Long): Flow<TrackEntity?>

  // TODO: add @Query
  fun getTracks(loopId: Long): Flow<List<TrackEntity>>

  // TODO: add @Insert
  fun addTrack(entity: TrackEntity): Long

  // TODO: add @Update
  fun updateTrack(entity: TrackEntity)

  // TODO: add @Delete
  fun deleteTrack(entity: TrackEntity)
}