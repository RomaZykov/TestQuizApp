package com.example.dailyquiztest.presentation.features.welcome.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.dailyquiztest.presentation.main_navigation.RouteBuilder

class WelcomeRouteBuilder : RouteBuilder {
    override fun build(navGraphBuilder: NavGraphBuilder, navController: NavHostController) {
        navGraphBuilder.composable<WelcomeRoute> {
            WelcomeRoute.Content(navController = navController)
        }
    }
}