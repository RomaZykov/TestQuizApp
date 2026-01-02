package com.example.dailyquiztest.fake

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.dailyquiztest.presentation.main_navigation.HistoryRouteProvider
import com.example.dailyquiztest.presentation.main_navigation.Route

class FakeHistoryRouteProvider : HistoryRouteProvider {
        var wasRouteCalled = false
        override fun route(): Route {
            wasRouteCalled = true
            return object : Route {
                @Composable
                override fun Content(navController: NavController) = Unit
            }
        }
    }