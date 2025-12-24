package com.example.dailyquiztest.presentation.main_navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface RouteBuilder {
    fun build(navGraphBuilder: NavGraphBuilder, navController: NavHostController)
}
