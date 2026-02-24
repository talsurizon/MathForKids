package com.mathforkids.domain.model

data class ExerciseResult(
    val exercise: Exercise,
    val userAnswer: Double?,
    val isCorrect: Boolean,
    val timeSpentMs: Long = 0
)
