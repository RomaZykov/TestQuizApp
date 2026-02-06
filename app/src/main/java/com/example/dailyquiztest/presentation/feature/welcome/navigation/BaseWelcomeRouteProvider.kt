package com.example.dailyquiztest.presentation.feature.welcome.navigation

import com.example.dailyquiztest.presentation.main_navigation.Route
import com.example.dailyquiztest.presentation.main_navigation.WelcomeRouteProvider

class BaseWelcomeRouteProvider : WelcomeRouteProvider {
    override fun route(): Route = WelcomeRoute
}