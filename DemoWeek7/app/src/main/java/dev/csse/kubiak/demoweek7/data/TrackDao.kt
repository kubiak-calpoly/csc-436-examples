package dev.csse.kubiak.demoweek7.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

interface TrackDao {
  @Query("SELECT * FROM Track WHERE id = :id")
  fun getTrack(id: Long): Flow<Track?>

  @Query("SELECT * FROM Track WHERE loop_id = :loopId ORDER BY trackNum")
  fun getTracks(loopId: Long): Flow<List<Track>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun addTrack(entity: Track): Long

  @Update
  fun updateTrack(entity: Track)

  @Delete
  fun deleteTrack(entity: Track)
}