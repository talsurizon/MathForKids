package com.mathforkids.domain.usecase

import com.mathforkids.domain.model.ExerciseResult
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Progress
import com.mathforkids.domain.model.Topic
import com.mathforkids.domain.repository.AchievementRepository
import com.mathforkids.domain.repository.ProgressRepository
import com.mathforkids.util.calculateScorePercent
import com.mathforkids.util.toStars
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SaveSessionResultsUseCase @Inject constructor(
    private val progressRepository: ProgressRepository,
    private val achievementRepository: AchievementRepository
) {
    suspend operator fun invoke(
        grade: Grade,
        topic: Topic,
        results: List<ExerciseResult>
    ) {
        val correct = results.count { it.isCorrect }
        val total = results.size
        val scorePercent = calculateScorePercent(correct, total)
        val stars = scorePercent.toStars()

        // Update progress
        val existing = progressRepository.getProgress(grade, topic).first()
        val updated = Progress(
            grade = grade,
            topic = topic,
            totalExercises = (existing?.totalExercises ?: 0) + total,
            correctAnswers = (existing?.correctAnswers ?: 0) + correct,
            bestStars = maxOf(existing?.bestStars ?: 0, stars),
            sessionsCompleted = (existing?.sessionsCompleted ?: 0) + 1
        )
        progressRepository.saveProgress(updated)

        // Check achievements
        checkAchievements(grade, stars, results)
    }

    private suspend fun checkAchievements(
        grade: Grade,
        stars: Int,
        results: List<ExerciseResult>
    ) {
        // First exercise
        achievementRepository.unlockAchievement("first_exercise")

        // Perfect score
        if (stars == 3) {
            achievementRepository.unlockAchievement("perfect_score")
        }

        // Total sessions
        val totalSessions = progressRepository.getTotalSessions().first()
        if (totalSessions >= 10) {
            achievementRepository.unlockAchievement("ten_sessions")
        }

        // Grade master
        val gradeProgress = progressRepository.getProgressForGrade(grade).first()
        val topics = Topic.topicsForGrade(grade)
        if (topics.all { topic -> gradeProgress.any { it.topic == topic && it.bestStars == 3 } }) {
            achievementRepository.unlockAchievement("${grade.name.lowercase()}_master")
        }

        // Streak of 5
        var streak = 0
        for (result in results) {
            if (result.isCorrect) streak++ else streak = 0
            if (streak >= 5) {
                achievementRepository.unlockAchievement("streak_5")
                break
            }
        }

        // Speed demon
        if (results.any { it.isCorrect && it.timeSpentMs < 3000 }) {
            achievementRepository.unlockAchievement("speed_demon")
        }

        // All topics tried
        val allProgress = progressRepository.getAllProgress().first()
        val allTopicsTried = Topic.entries.all { topic ->
            allProgress.any { it.topic == topic && it.totalExercises > 0 }
        }
        if (allTopicsTried) {
            achievementRepository.unlockAchievement("all_topics")
        }
    }
}
