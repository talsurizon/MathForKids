package com.mathforkids.domain.generator

import com.mathforkids.domain.model.AnswerType
import com.mathforkids.domain.model.Exercise
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Topic
import javax.inject.Inject
import kotlin.random.Random

class Grade4Generator @Inject constructor() : ExerciseGenerator {
    override val grade = Grade.GRADE_4

    override fun generate(topic: Topic, count: Int): List<Exercise> =
        (1..count).map { generateSingle(topic) }

    private fun generateSingle(topic: Topic): Exercise {
        val (question, answer) = when (topic) {
            Topic.LONG_MULTIPLICATION -> {
                val a = Random.nextInt(10, 100)
                val b = Random.nextInt(10, 100)
                "$a × $b = ?" to (a * b).toDouble()
            }
            Topic.LONG_DIVISION -> {
                val divisor = Random.nextInt(2, 20)
                val quotient = Random.nextInt(10, 100)
                val dividend = divisor * quotient
                "$dividend ÷ $divisor = ?" to quotient.toDouble()
            }
            else -> {
                val a = Random.nextInt(10, 100)
                val b = Random.nextInt(10, 100)
                "$a × $b = ?" to (a * b).toDouble()
            }
        }

        return Exercise(question, answer, AnswerType.FREE_FORM, emptyList(), grade, topic)
    }
}
