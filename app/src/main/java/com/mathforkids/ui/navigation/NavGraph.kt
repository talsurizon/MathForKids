package com.mathforkids.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Topic
import com.mathforkids.ui.screen.achievements.AchievementsScreen
import com.mathforkids.ui.screen.exercise.ExerciseScreen
import com.mathforkids.ui.screen.home.HomeScreen
import com.mathforkids.ui.screen.results.ResultsScreen
import com.mathforkids.ui.screen.topics.TopicsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(
                windowSizeClass = windowSizeClass,
                onGradeSelected = { grade ->
                    navController.navigate(Screen.Topics.createRoute(grade.level))
                },
                onAchievementsClick = {
                    navController.navigate(Screen.Achievements.route)
                }
            )
        }

        composable(
            route = Screen.Topics.route,
            arguments = listOf(navArgument("gradeLevel") { type = NavType.IntType })
        ) { backStackEntry ->
            val gradeLevel = backStackEntry.arguments?.getInt("gradeLevel") ?: 1
            val grade = Grade.fromLevel(gradeLevel)
            TopicsScreen(
                grade = grade,
                windowSizeClass = windowSizeClass,
                onTopicSelected = { topic ->
                    navController.navigate(
                        Screen.Exercise.createRoute(gradeLevel, topic.name)
                    )
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Exercise.route,
            arguments = listOf(
                navArgument("gradeLevel") { type = NavType.IntType },
                navArgument("topicName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val gradeLevel = backStackEntry.arguments?.getInt("gradeLevel") ?: 1
            val topicName = backStackEntry.arguments?.getString("topicName") ?: ""
            ExerciseScreen(
                onSessionComplete = { correct, total ->
                    navController.navigate(
                        Screen.Results.createRoute(gradeLevel, topicName, correct, total)
                    ) {
                        popUpTo(Screen.Topics.createRoute(gradeLevel))
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Results.route,
            arguments = listOf(
                navArgument("gradeLevel") { type = NavType.IntType },
                navArgument("topicName") { type = NavType.StringType },
                navArgument("correct") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val gradeLevel = backStackEntry.arguments?.getInt("gradeLevel") ?: 1
            val topicName = backStackEntry.arguments?.getString("topicName") ?: ""
            val correct = backStackEntry.arguments?.getInt("correct") ?: 0
            val total = backStackEntry.arguments?.getInt("total") ?: 0
            ResultsScreen(
                correct = correct,
                total = total,
                onTryAgain = {
                    navController.navigate(
                        Screen.Exercise.createRoute(gradeLevel, topicName)
                    ) {
                        popUpTo(Screen.Topics.createRoute(gradeLevel))
                    }
                },
                onBackToTopics = {
                    navController.popBackStack(Screen.Topics.createRoute(gradeLevel), false)
                },
                onBackToHome = {
                    navController.popBackStack(Screen.Home.route, false)
                }
            )
        }

        composable(Screen.Achievements.route) {
            AchievementsScreen(
                windowSizeClass = windowSizeClass,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
