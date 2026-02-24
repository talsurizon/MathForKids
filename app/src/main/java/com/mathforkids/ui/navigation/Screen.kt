package com.mathforkids.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Topics : Screen("topics/{gradeLevel}") {
        fun createRoute(gradeLevel: Int) = "topics/$gradeLevel"
    }
    data object Exercise : Screen("exercise/{gradeLevel}/{topicName}") {
        fun createRoute(gradeLevel: Int, topicName: String) = "exercise/$gradeLevel/$topicName"
    }
    data object Results : Screen("results/{gradeLevel}/{topicName}/{correct}/{total}") {
        fun createRoute(gradeLevel: Int, topicName: String, correct: Int, total: Int) =
            "results/$gradeLevel/$topicName/$correct/$total"
    }
    data object Achievements : Screen("achievements")
}
