package com.mathforkids.domain.model

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
) {
    companion object {
        val ALL = listOf(
            Achievement("first_exercise", "התחלה!", "השלמת את התרגיל הראשון", "🌟"),
            Achievement("perfect_score", "מושלם!", "קיבלת ציון מושלם בסדרת תרגילים", "⭐"),
            Achievement("ten_sessions", "מתאמן!", "השלמת 10 סדרות תרגילים", "💪"),
            Achievement("grade_1_master", "מאסטר כיתה א׳", "השלמת את כל הנושאים בכיתה א׳ עם 3 כוכבים", "🏆"),
            Achievement("grade_2_master", "מאסטר כיתה ב׳", "השלמת את כל הנושאים בכיתה ב׳ עם 3 כוכבים", "🏆"),
            Achievement("grade_3_master", "מאסטר כיתה ג׳", "השלמת את כל הנושאים בכיתה ג׳ עם 3 כוכבים", "🏆"),
            Achievement("grade_4_master", "מאסטר כיתה ד׳", "השלמת את כל הנושאים בכיתה ד׳ עם 3 כוכבים", "🏆"),
            Achievement("grade_5_master", "מאסטר כיתה ה׳", "השלמת את כל הנושאים בכיתה ה׳ עם 3 כוכבים", "🏆"),
            Achievement("grade_6_master", "מאסטר כיתה ו׳", "השלמת את כל הנושאים בכיתה ו׳ עם 3 כוכבים", "🏆"),
            Achievement("streak_5", "רצף!", "ענית נכון על 5 שאלות ברצף", "🔥"),
            Achievement("speed_demon", "מהיר!", "ענית נכון על שאלה תוך פחות מ-3 שניות", "⚡"),
            Achievement("all_topics", "חוקר!", "ניסית את כל הנושאים", "🧭")
        )
    }
}
