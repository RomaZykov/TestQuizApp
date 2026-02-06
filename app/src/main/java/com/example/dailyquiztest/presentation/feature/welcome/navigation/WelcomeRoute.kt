package com.example.dailyquiztest.presentation.feature.welcome.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.dailyquiztest.presentation.main_navigation.Route
import com.example.dailyquiztest.presentation.feature.welcome.WelcomeScreenNav
import kotlinx.serialization.Serializable

@Serializable
object WelcomeRoute : Route {
    @Composable
    override fun Content(navController: NavController) {
        WelcomeScreenNav(navController = navController)
    }
}
