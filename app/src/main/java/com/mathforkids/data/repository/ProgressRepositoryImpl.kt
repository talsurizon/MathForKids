package com.mathforkids.data.repository

import com.mathforkids.data.local.dao.ProgressDao
import com.mathforkids.data.local.entity.ProgressEntity
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Progress
import com.mathforkids.domain.model.Topic
import com.mathforkids.domain.repository.ProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProgressRepositoryImpl @Inject constructor(
    private val progressDao: ProgressDao
) : ProgressRepository {

    override fun getProgress(grade: Grade, topic: Topic): Flow<Progress?> =
        progressDao.getProgress(grade.level, topic.name).map { entity ->
            entity?.toDomain(grade, topic)
        }

    override fun getProgressForGrade(grade: Grade): Flow<List<Progress>> =
        progressDao.getProgressForGrade(grade.level).map { entities ->
            entities.map { entity ->
                entity.toDomain(grade, Topic.valueOf(entity.topicName))
            }
        }

    override fun getAllProgress(): Flow<List<Progress>> =
        progressDao.getAllProgress().map { entities ->
            entities.map { entity ->
                entity.toDomain(
                    Grade.fromLevel(entity.gradeLevel),
                    Topic.valueOf(entity.topicName)
                )
            }
        }

    override fun getTotalSessions(): Flow<Int> =
        progressDao.getTotalSessions().map { it ?: 0 }

    override suspend fun saveProgress(progress: Progress) {
        progressDao.upsertProgress(progress.toEntity())
    }

    private fun ProgressEntity.toDomain(grade: Grade, topic: Topic) = Progress(
        grade = grade,
        topic = topic,
        totalExercises = totalExercises,
        correctAnswers = correctAnswers,
        bestStars = bestStars,
        sessionsCompleted = sessionsCompleted
    )

    private fun Progress.toEntity() = ProgressEntity(
        gradeLevel = grade.level,
        topicName = topic.name,
        totalExercises = totalExercises,
        correctAnswers = correctAnswers,
        bestStars = bestStars,
        sessionsCompleted = sessionsCompleted
    )
}
