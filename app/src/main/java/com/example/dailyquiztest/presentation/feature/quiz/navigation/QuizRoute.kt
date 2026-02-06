package com.example.dailyquiztest.presentation.feature.quiz.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.dailyquiztest.presentation.feature.quiz.QuizScreen
import com.example.dailyquiztest.presentation.main_navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object QuizRoute : Route {
    @Composable
    override fun Content(navController: NavController) {
        QuizScreen(navController)
    }
}
