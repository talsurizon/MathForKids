package com.mathforkids.domain.usecase

import com.mathforkids.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class CheckAnswerUseCaseTest {

    private val useCase = CheckAnswerUseCase()

    @Test
    fun `correct answer returns isCorrect true`() {
        val exercise = Exercise(
            question = "2 + 3 = ?",
            correctAnswer = 5.0,
            answerType = AnswerType.FREE_FORM,
            grade = Grade.GRADE_1,
            topic = Topic.ADDITION
        )

        val result = useCase(exercise, 5.0, 1000)
        assertTrue(result.isCorrect)
        assertEquals(5.0, result.userAnswer!!, 0.001)
    }

    @Test
    fun `wrong answer returns isCorrect false`() {
        val exercise = Exercise(
            question = "2 + 3 = ?",
            correctAnswer = 5.0,
            answerType = AnswerType.FREE_FORM,
            grade = Grade.GRADE_1,
            topic = Topic.ADDITION
        )

        val result = useCase(exercise, 4.0, 1000)
        assertFalse(result.isCorrect)
    }

    @Test
    fun `null answer returns isCorrect false`() {
        val exercise = Exercise(
            question = "2 + 3 = ?",
            correctAnswer = 5.0,
            answerType = AnswerType.FREE_FORM,
            grade = Grade.GRADE_1,
            topic = Topic.ADDITION
        )

        val result = useCase(exercise, null, 1000)
        assertFalse(result.isCorrect)
        assertNull(result.userAnswer)
    }

    @Test
    fun `time spent is recorded`() {
        val exercise = Exercise(
            question = "2 + 3 = ?",
            correctAnswer = 5.0,
            answerType = AnswerType.FREE_FORM,
            grade = Grade.GRADE_1,
            topic = Topic.ADDITION
        )

        val result = useCase(exercise, 5.0, 2500)
        assertEquals(2500, result.timeSpentMs)
    }
}
