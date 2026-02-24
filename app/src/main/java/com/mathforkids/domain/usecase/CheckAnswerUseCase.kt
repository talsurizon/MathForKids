package com.mathforkids.domain.usecase

import com.mathforkids.domain.model.Exercise
import com.mathforkids.domain.model.ExerciseResult
import javax.inject.Inject
import kotlin.math.abs

class CheckAnswerUseCase @Inject constructor() {

    operator fun invoke(exercise: Exercise, userAnswer: Double?, timeSpentMs: Long = 0): ExerciseResult {
        val isCorrect = userAnswer != null && abs(userAnswer - exercise.correctAnswer) < 0.01
        return ExerciseResult(
            exercise = exercise,
            userAnswer = userAnswer,
            isCorrect = isCorrect,
            timeSpentMs = timeSpentMs
        )
    }
}
