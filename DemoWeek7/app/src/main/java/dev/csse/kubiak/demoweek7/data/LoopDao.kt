package dev.csse.kubiak.demoweek7.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LoopDao {
  // TODO: add @Query
  fun getLoop(id: Long): Flow<LoopEntity?>

  // TODO: add @Query
  fun getLoops(): Flow<List<LoopEntity>>

  // TODO: add @Insert
  fun addLoop(entity: LoopEntity): Long

  // TODO: add @Update
  fun updateLoop(entity: LoopEntity)

  // TODO: add @Delete
  fun deleteLoop(entity: LoopEntity)
}