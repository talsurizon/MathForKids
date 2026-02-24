package com.mathforkids.domain.generator

import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Topic
import org.junit.Assert.*
import org.junit.Test

class Grade3GeneratorTest {

    private val generator = Grade3Generator()

    @Test
    fun `multiplication exercises have valid answers within tables`() {
        val exercises = generator.generate(Topic.MULTIPLICATION, 20)
        exercises.forEach { exercise ->
            assertTrue("Answer should be > 0", exercise.correctAnswer > 0)
            assertTrue("Answer should be <= 100", exercise.correctAnswer <= 100)
            assertEquals(Grade.GRADE_3, exercise.grade)
        }
    }

    @Test
    fun `division exercises produce whole number answers`() {
        val exercises = generator.generate(Topic.DIVISION, 20)
        exercises.forEach { exercise ->
            val answer = exercise.correctAnswer
            assertEquals("Division should produce whole number", answer, answer.toLong().toDouble(), 0.001)
            assertTrue("Answer should be > 0", answer > 0)
        }
    }
}
