package com.mathforkids.domain.generator

import com.mathforkids.domain.model.AnswerType
import com.mathforkids.domain.model.Exercise
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Topic
import javax.inject.Inject
import kotlin.random.Random

class Grade6Generator @Inject constructor() : ExerciseGenerator {
    override val grade = Grade.GRADE_6

    override fun generate(topic: Topic, count: Int): List<Exercise> =
        (1..count).map { generateSingle(topic) }

    private fun generateSingle(topic: Topic): Exercise {
        val (question, answer) = when (topic) {
            Topic.ORDER_OF_OPERATIONS -> generateOrderOfOps()
            Topic.PERCENTAGES -> generatePercentage()
            else -> generateOrderOfOps()
        }

        return Exercise(question, answer, AnswerType.FREE_FORM, emptyList(), grade, topic)
    }

    private fun generateOrderOfOps(): Pair<String, Double> {
        val type = Random.nextInt(3)
        return when (type) {
            0 -> {
                // a + b × c
                val a = Random.nextInt(1, 20)
                val b = Random.nextInt(1, 10)
                val c = Random.nextInt(1, 10)
                "$a + $b × $c = ?" to (a + b * c).toDouble()
            }
            1 -> {
                // (a + b) × c
                val a = Random.nextInt(1, 15)
                val b = Random.nextInt(1, 15)
                val c = Random.nextInt(2, 8)
                "($a + $b) × $c = ?" to ((a + b) * c).toDouble()
            }
            else -> {
                // a × b - c
                val a = Random.nextInt(2, 10)
                val b = Random.nextInt(2, 10)
                val c = Random.nextInt(1, a * b)
                "$a × $b - $c = ?" to (a * b - c).toDouble()
            }
        }
    }

    private fun generatePercentage(): Pair<String, Double> {
        val percent = listOf(10, 20, 25, 50, 75).random()
        val number = listOf(20, 40, 50, 60, 80, 100, 200, 500).random()
        val answer = number * percent / 100.0
        "$percent% מתוך $number = ?" to answer
    }
}
