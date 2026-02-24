package com.mathforkids.domain.generator

import com.mathforkids.domain.model.AnswerType
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Topic
import org.junit.Assert.*
import org.junit.Test

class Grade1GeneratorTest {

    private val generator = Grade1Generator()

    @Test
    fun `generate returns correct number of exercises`() {
        val exercises = generator.generate(Topic.ADDITION, 10)
        assertEquals(10, exercises.size)
    }

    @Test
    fun `generate addition exercises have valid answers`() {
        val exercises = generator.generate(Topic.ADDITION, 20)
        exercises.forEach { exercise ->
            assertTrue("Answer should be >= 0", exercise.correctAnswer >= 0)
            assertTrue("Answer should be <= 20", exercise.correctAnswer <= 20)
            assertEquals(Grade.GRADE_1, exercise.grade)
            assertEquals(Topic.ADDITION, exercise.topic)
        }
    }

    @Test
    fun `generate subtraction exercises have non-negative answers`() {
        val exercises = generator.generate(Topic.SUBTRACTION, 20)
        exercises.forEach { exercise ->
            assertTrue("Answer should be >= 0", exercise.correctAnswer >= 0)
        }
    }

    @Test
    fun `generate exercises have correct answer type mix`() {
        val exercises = generator.generate(Topic.ADDITION, 10)
        val mcCount = exercises.count { it.answerType == AnswerType.MULTIPLE_CHOICE }
        val ffCount = exercises.count { it.answerType == AnswerType.FREE_FORM }
        assertTrue("Should have ~70% MC", mcCount >= 5)
        assertTrue("Should have some free-form", ffCount >= 1)
    }

    @Test
    fun `multiple choice exercises have 4 choices`() {
        val exercises = generator.generate(Topic.ADDITION, 10)
        exercises.filter { it.answerType == AnswerType.MULTIPLE_CHOICE }.forEach {
            assertEquals(4, it.choices.size)
            assertTrue("Choices must contain correct answer", it.choices.contains(it.correctAnswer))
        }
    }
}
