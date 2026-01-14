package com.example.dailyquiztest.testdoubles

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.dailyquiztest.presentation.main_navigation.QuizRouteProvider
import com.example.dailyquiztest.presentation.main_navigation.Route

class FakeQuizRouteProvider : QuizRouteProvider {
        var wasRouteCalled = false
        override fun route(): Route {
            wasRouteCalled = true
            return object : Route {
                @Composable
                override fun Content(navController: NavController) = Unit
            }
        }
    }