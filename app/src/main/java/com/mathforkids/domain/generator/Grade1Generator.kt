package com.mathforkids.domain.generator

import com.mathforkids.domain.model.AnswerType
import com.mathforkids.domain.model.Exercise
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Topic
import javax.inject.Inject
import kotlin.random.Random

class Grade1Generator @Inject constructor() : ExerciseGenerator {
    override val grade = Grade.GRADE_1

    override fun generate(topic: Topic, count: Int): List<Exercise> =
        (1..count).map { generateSingle(topic, it <= count * 0.7) }

    private fun generateSingle(topic: Topic, preferMultipleChoice: Boolean): Exercise {
        val a = Random.nextInt(1, 11)
        val b = Random.nextInt(1, 11)

        val (question, answer) = when (topic) {
            Topic.ADDITION -> {
                val x = Random.nextInt(0, 11)
                val y = Random.nextInt(0, 21 - x)
                "$x + $y = ?" to (x + y).toDouble()
            }
            Topic.SUBTRACTION -> {
                val x = Random.nextInt(1, 21)
                val y = Random.nextInt(0, x + 1)
                "$x - $y = ?" to (x - y).toDouble()
            }
            else -> "$a + $b = ?" to (a + b).toDouble()
        }

        val answerType = if (preferMultipleChoice) AnswerType.MULTIPLE_CHOICE else AnswerType.FREE_FORM
        val choices = if (answerType == AnswerType.MULTIPLE_CHOICE) {
            generateChoices(answer)
        } else emptyList()

        return Exercise(question, answer, answerType, choices, grade, topic)
    }
}

private fun generateChoices(correct: Double): List<Double> {
    val choices = mutableSetOf(correct)
    while (choices.size < 4) {
        val offset = Random.nextInt(1, 6) * if (Random.nextBoolean()) 1 else -1
        val wrong = correct + offset
        if (wrong >= 0) choices.add(wrong)
    }
    return choices.shuffled()
}
