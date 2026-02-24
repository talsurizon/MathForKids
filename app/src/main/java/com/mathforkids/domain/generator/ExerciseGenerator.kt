package com.mathforkids.domain.generator

import com.mathforkids.domain.model.Exercise
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Topic

interface ExerciseGenerator {
    val grade: Grade
    fun generate(topic: Topic, count: Int = 10): List<Exercise>
}
