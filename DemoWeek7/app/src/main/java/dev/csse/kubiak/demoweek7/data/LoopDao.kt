package dev.csse.kubiak.demoweek7.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LoopDao {
  @Query("SELECT * FROM Loop WHERE id = :id")
  fun getLoop(id: Long): Flow<Loop?>

  @Query("SELECT * FROM Loop ORDER BY title COLLATE NOCASE")
  fun getLoops(): Flow<List<Loop>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun addLoop(entity: Loop): Long

  @Update
  fun updateLoop(entity: Loop)

  @Delete
  fun deleteLoop(entity: Loop)
}