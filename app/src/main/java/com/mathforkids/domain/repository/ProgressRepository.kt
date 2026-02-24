package com.mathforkids.domain.repository

import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Progress
import com.mathforkids.domain.model.Topic
import kotlinx.coroutines.flow.Flow

interface ProgressRepository {
    fun getProgress(grade: Grade, topic: Topic): Flow<Progress?>
    fun getProgressForGrade(grade: Grade): Flow<List<Progress>>
    fun getAllProgress(): Flow<List<Progress>>
    fun getTotalSessions(): Flow<Int>
    suspend fun saveProgress(progress: Progress)
}
