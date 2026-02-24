package com.mathforkids.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mathforkids.data.local.entity.ProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {

    @Query("SELECT * FROM progress WHERE gradeLevel = :gradeLevel AND topicName = :topicName")
    fun getProgress(gradeLevel: Int, topicName: String): Flow<ProgressEntity?>

    @Query("SELECT * FROM progress WHERE gradeLevel = :gradeLevel")
    fun getProgressForGrade(gradeLevel: Int): Flow<List<ProgressEntity>>

    @Query("SELECT * FROM progress")
    fun getAllProgress(): Flow<List<ProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(progress: ProgressEntity)

    @Query("SELECT SUM(sessionsCompleted) FROM progress")
    fun getTotalSessions(): Flow<Int?>
}
