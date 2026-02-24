package com.mathforkids.domain.model

data class Progress(
    val grade: Grade,
    val topic: Topic,
    val totalExercises: Int = 0,
    val correctAnswers: Int = 0,
    val bestStars: Int = 0,
    val sessionsCompleted: Int = 0
) {
    val accuracy: Float
        get() = if (totalExercises > 0) correctAnswers.toFloat() / totalExercises else 0f
}
