package com.mathforkids.domain.model

enum class AnswerType {
    MULTIPLE_CHOICE,
    FREE_FORM
}

data class Exercise(
    val question: String,
    val correctAnswer: Double,
    val answerType: AnswerType,
    val choices: List<Double> = emptyList(),
    val grade: Grade,
    val topic: Topic,
    val correctAnswerFraction: String? = null
) {
    val correctAnswerDisplay: String
        get() = correctAnswerFraction ?: if (correctAnswer == correctAnswer.toLong().toDouble()) {
            correctAnswer.toLong().toString()
        } else {
            correctAnswer.toString()
        }
}
