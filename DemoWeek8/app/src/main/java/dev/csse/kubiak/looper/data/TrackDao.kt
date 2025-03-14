package dev.csse.kubiak.looper.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
  @Query("SELECT * FROM TrackEntity WHERE id = :id")
  fun getTrack(id: Long): Flow<TrackEntity?>

  @Query("SELECT * FROM TrackEntity WHERE loop_id = :loopId ORDER BY trackNum")
  fun getTracks(loopId: Long): Flow<List<TrackEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun addTrack(entity: TrackEntity): Long

  @Update
  fun updateTrack(entity: TrackEntity)

  @Delete
  fun deleteTrack(entity: TrackEntity)
}