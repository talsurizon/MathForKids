package com.mathforkids.data.local.entity

import androidx.room.Entity

@Entity(tableName = "progress", primaryKeys = ["gradeLevel", "topicName"])
data class ProgressEntity(
    val gradeLevel: Int,
    val topicName: String,
    val totalExercises: Int = 0,
    val correctAnswers: Int = 0,
    val bestStars: Int = 0,
    val sessionsCompleted: Int = 0
)
