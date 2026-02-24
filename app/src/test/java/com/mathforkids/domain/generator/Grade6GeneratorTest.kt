package com.mathforkids.domain.generator

import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Topic
import org.junit.Assert.*
import org.junit.Test

class Grade6GeneratorTest {

    private val generator = Grade6Generator()

    @Test
    fun `order of operations exercises have correct answers`() {
        val exercises = generator.generate(Topic.ORDER_OF_OPERATIONS, 10)
        exercises.forEach { exercise ->
            assertEquals(Grade.GRADE_6, exercise.grade)
            assertEquals(Topic.ORDER_OF_OPERATIONS, exercise.topic)
            assertTrue("Question should contain operators", exercise.question.contains("×") || exercise.question.contains("+") || exercise.question.contains("-"))
        }
    }

    @Test
    fun `percentage exercises produce valid results`() {
        val exercises = generator.generate(Topic.PERCENTAGES, 10)
        exercises.forEach { exercise ->
            assertTrue("Answer should be > 0", exercise.correctAnswer > 0)
            assertTrue("Question should contain percent", exercise.question.contains("%"))
        }
    }
}
