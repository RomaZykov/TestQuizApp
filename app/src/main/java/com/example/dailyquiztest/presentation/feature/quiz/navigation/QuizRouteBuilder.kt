package com.example.dailyquiztest.presentation.feature.quiz.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.dailyquiztest.presentation.main_navigation.RouteBuilder

class QuizRouteBuilder : RouteBuilder {
    override fun build(navGraphBuilder: NavGraphBuilder, navController: NavHostController) {
        navGraphBuilder.composable<QuizRoute> {
            QuizRoute.Content(navController = navController)
        }
    }
}