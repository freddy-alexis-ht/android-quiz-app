package com.sunday.quiz1.ui.common

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sunday.quiz1.ui.home.HomeScreen
import com.sunday.quiz1.ui.home.HomeVM
import com.sunday.quiz1.ui.question.QuestionScreen
import com.sunday.quiz1.ui.question.QuestionVM
import com.sunday.quiz1.ui.result.ResultScreen
import com.sunday.quiz1.ui.result.ResultVM

@Composable
fun Navigation(homeVM: HomeVM, questionVM: QuestionVM, resultVM: ResultVM) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.QUIZ_HOME
    ) {
        composable(
            route = Routes.QUIZ_HOME + "?mem={mem}",
            arguments = listOf(
                navArgument(name = "mem") {
                    type = NavType.BoolType
                    defaultValue = true
                }
            )
        ) { entry ->
            HomeScreen(
                onNavigate = { navController.navigate(it.route) },
                homeVM = homeVM,
                mem = entry.arguments?.getBoolean("mem")
            )
        }
        composable(Routes.QUIZ_QUESTION + "?newQuiz={newQuiz}",
            arguments = listOf(
                navArgument(name = "newQuiz") {
                    type = NavType.BoolType
                    defaultValue = true
                }
            )
        ) { entry ->
            QuestionScreen(
                onNavigate = { navController.navigate(it.route) },
                questionVM = questionVM,
                newQuiz = entry.arguments?.getBoolean("newQuiz")
            )
        }
        composable(Routes.QUIZ_RESULTS) {
            ResultScreen(
                onNavigate = { navController.navigate(it.route) },
                resultVM = resultVM,
                questionVM = questionVM
            )
        }
    }
}