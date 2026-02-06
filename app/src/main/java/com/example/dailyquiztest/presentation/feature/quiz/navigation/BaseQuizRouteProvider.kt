package com.example.dailyquiztest.presentation.feature.quiz.navigation

import com.example.dailyquiztest.presentation.main_navigation.QuizRouteProvider
import com.example.dailyquiztest.presentation.main_navigation.Route

class BaseQuizRouteProvider : QuizRouteProvider {
    override fun route(): Route = QuizRoute
}