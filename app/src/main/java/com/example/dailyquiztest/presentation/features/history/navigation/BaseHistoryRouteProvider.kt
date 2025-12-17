package com.example.dailyquiztest.presentation.features.history.navigation

import com.example.dailyquiztest.presentation.main_navigation.HistoryRouteProvider
import com.example.dailyquiztest.presentation.main_navigation.Route

class BaseHistoryRouteProvider : HistoryRouteProvider {
    override fun route(): Route = HistoryRoute
}