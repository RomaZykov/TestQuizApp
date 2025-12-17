package com.example.dailyquiztest.presentation.features.history.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.dailyquiztest.presentation.main_navigation.RouteBuilder

class HistoryRouteBuilder : RouteBuilder {
    override fun build(navGraphBuilder: NavGraphBuilder, navController: NavHostController) {
        navGraphBuilder.composable<HistoryRoute> {
            HistoryRoute.Content(navController = navController)
        }
    }
}