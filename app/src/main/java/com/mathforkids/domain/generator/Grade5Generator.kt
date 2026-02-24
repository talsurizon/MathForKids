package com.mathforkids.domain.generator

import com.mathforkids.domain.model.AnswerType
import com.mathforkids.domain.model.Exercise
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Topic
import javax.inject.Inject
import kotlin.random.Random

class Grade5Generator @Inject constructor() : ExerciseGenerator {
    override val grade = Grade.GRADE_5

    override fun generate(topic: Topic, count: Int): List<Exercise> =
        (1..count).map { generateSingle(topic) }

    private fun generateSingle(topic: Topic): Exercise {
        val (question, answer) = when (topic) {
            Topic.FRACTIONS -> generateFractionExercise()
            Topic.DECIMALS -> generateDecimalExercise()
            else -> generateFractionExercise()
        }

        return Exercise(question, answer, AnswerType.FREE_FORM, emptyList(), grade, topic)
    }

    private fun generateFractionExercise(): Pair<String, Double> {
        val denominator = listOf(2, 3, 4, 5, 6, 8, 10).random()
        val num1 = Random.nextInt(1, denominator)
        val num2 = Random.nextInt(1, denominator)

        return if (Random.nextBoolean()) {
            val result = num1 + num2
            val wholes = result / denominator
            val remainder = result % denominator
            val display = if (wholes > 0 && remainder > 0) "$wholes ו-$remainder/$denominator"
            else if (wholes > 0) "$wholes"
            else "$remainder/$denominator"
            "$num1/$denominator + $num2/$denominator = ?" to result.toDouble() / denominator
        } else {
            val bigger = maxOf(num1, num2)
            val smaller = minOf(num1, num2)
            val result = bigger - smaller
            "$bigger/$denominator - $smaller/$denominator = ?" to result.toDouble() / denominator
        }
    }

    private fun generateDecimalExercise(): Pair<String, Double> {
        val a = Random.nextInt(1, 100).toDouble() / 10
        val b = Random.nextInt(1, 100).toDouble() / 10

        return if (Random.nextBoolean()) {
            val result = Math.round((a + b) * 10) / 10.0
            "$a + $b = ?" to result
        } else {
            val bigger = maxOf(a, b)
            val smaller = minOf(a, b)
            val result = Math.round((bigger - smaller) * 10) / 10.0
            "$bigger - $smaller = ?" to result
        }
    }
}
