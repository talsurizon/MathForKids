package com.mathforkids.domain.usecase

import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Progress
import com.mathforkids.domain.model.Topic
import com.mathforkids.domain.repository.ProgressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProgressUseCase @Inject constructor(
    private val progressRepository: ProgressRepository
) {
    fun forGradeAndTopic(grade: Grade, topic: Topic): Flow<Progress?> =
        progressRepository.getProgress(grade, topic)

    fun forGrade(grade: Grade): Flow<List<Progress>> =
        progressRepository.getProgressForGrade(grade)

    fun all(): Flow<List<Progress>> =
        progressRepository.getAllProgress()

    fun totalSessions(): Flow<Int> =
        progressRepository.getTotalSessions()
}
