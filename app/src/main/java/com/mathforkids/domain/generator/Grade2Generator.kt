package com.mathforkids.domain.generator

import com.mathforkids.domain.model.AnswerType
import com.mathforkids.domain.model.Exercise
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Topic
import javax.inject.Inject
import kotlin.random.Random

class Grade2Generator @Inject constructor() : ExerciseGenerator {
    override val grade = Grade.GRADE_2

    override fun generate(topic: Topic, count: Int): List<Exercise> =
        (1..count).map { generateSingle(topic, it <= count * 0.7) }

    private fun generateSingle(topic: Topic, preferMultipleChoice: Boolean): Exercise {
        val (question, answer) = when (topic) {
            Topic.ADDITION -> {
                val a = Random.nextInt(10, 51)
                val b = Random.nextInt(10, 101 - a)
                "$a + $b = ?" to (a + b).toDouble()
            }
            Topic.SUBTRACTION -> {
                val a = Random.nextInt(20, 101)
                val b = Random.nextInt(1, a)
                "$a - $b = ?" to (a - b).toDouble()
            }
            else -> {
                val a = Random.nextInt(10, 51)
                val b = Random.nextInt(10, 51)
                "$a + $b = ?" to (a + b).toDouble()
            }
        }

        val answerType = if (preferMultipleChoice) AnswerType.MULTIPLE_CHOICE else AnswerType.FREE_FORM
        val choices = if (answerType == AnswerType.MULTIPLE_CHOICE) {
            generateChoices(answer, 10)
        } else emptyList()

        return Exercise(question, answer, answerType, choices, grade, topic)
    }
}

private fun generateChoices(correct: Double, range: Int): List<Double> {
    val choices = mutableSetOf(correct)
    while (choices.size < 4) {
        val offset = Random.nextInt(1, range + 1) * if (Random.nextBoolean()) 1 else -1
        val wrong = correct + offset
        if (wrong >= 0) choices.add(wrong)
    }
    return choices.shuffled()
}
