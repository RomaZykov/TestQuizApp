package com.example.dailyquiztest.presentation.feature.history.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.dailyquiztest.presentation.feature.history.HistoryScreenNav
import com.example.dailyquiztest.presentation.main_navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object HistoryRoute : Route {
    @Composable
    override fun Content(navController: NavController) {
        HistoryScreenNav(navController)
    }
}