package com.mathforkids.domain.model

enum class Topic(val hebrewName: String, val icon: String) {
    ADDITION("חיבור", "➕"),
    SUBTRACTION("חיסור", "➖"),
    MULTIPLICATION("כפל", "✖️"),
    DIVISION("חילוק", "➗"),
    LONG_MULTIPLICATION("כפל ארוך", "✖️"),
    LONG_DIVISION("חילוק ארוך", "➗"),
    FRACTIONS("שברים", "½"),
    DECIMALS("מספרים עשרוניים", "0.5"),
    ORDER_OF_OPERATIONS("סדר פעולות חשבון", "🔢"),
    PERCENTAGES("אחוזים", "%");

    companion object {
        fun topicsForGrade(grade: Grade): List<Topic> = when (grade) {
            Grade.GRADE_1 -> listOf(ADDITION, SUBTRACTION)
            Grade.GRADE_2 -> listOf(ADDITION, SUBTRACTION)
            Grade.GRADE_3 -> listOf(MULTIPLICATION, DIVISION)
            Grade.GRADE_4 -> listOf(LONG_MULTIPLICATION, LONG_DIVISION)
            Grade.GRADE_5 -> listOf(FRACTIONS, DECIMALS)
            Grade.GRADE_6 -> listOf(ORDER_OF_OPERATIONS, PERCENTAGES)
        }
    }
}
