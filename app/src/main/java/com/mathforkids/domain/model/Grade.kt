package com.mathforkids.domain.model

enum class Grade(val level: Int, val hebrewName: String) {
    GRADE_1(1, "כיתה א׳"),
    GRADE_2(2, "כיתה ב׳"),
    GRADE_3(3, "כיתה ג׳"),
    GRADE_4(4, "כיתה ד׳"),
    GRADE_5(5, "כיתה ה׳"),
    GRADE_6(6, "כיתה ו׳");

    companion object {
        fun fromLevel(level: Int): Grade = entries.first { it.level == level }
    }
}
