package dev.csse.kubiak.looper.data

import androidx.room.*
import dev.csse.kubiak.looper.data.LoopEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoopDao {
  @Query("SELECT * FROM LoopEntity WHERE id = :id")
  fun getLoop(id: Long): Flow<LoopEntity?>

  @Query("SELECT * FROM LoopEntity ORDER BY title COLLATE NOCASE")
  fun getLoops(): Flow<List<LoopEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun addLoop(entity: LoopEntity): Long

  @Update
  fun updateLoop(entity: LoopEntity)

  @Delete
  fun deleteLoop(entity: LoopEntity)
}