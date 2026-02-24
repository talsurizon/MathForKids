package com.mathforkids.domain.usecase

import com.mathforkids.domain.generator.*
import com.mathforkids.domain.model.Exercise
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Topic
import javax.inject.Inject

class GenerateExercisesUseCase @Inject constructor(
    private val grade1Generator: Grade1Generator,
    private val grade2Generator: Grade2Generator,
    private val grade3Generator: Grade3Generator,
    private val grade4Generator: Grade4Generator,
    private val grade5Generator: Grade5Generator,
    private val grade6Generator: Grade6Generator
) {
    operator fun invoke(grade: Grade, topic: Topic, count: Int = 10): List<Exercise> {
        val generator = when (grade) {
            Grade.GRADE_1 -> grade1Generator
            Grade.GRADE_2 -> grade2Generator
            Grade.GRADE_3 -> grade3Generator
            Grade.GRADE_4 -> grade4Generator
            Grade.GRADE_5 -> grade5Generator
            Grade.GRADE_6 -> grade6Generator
        }
        return generator.generate(topic, count)
    }
}
