package com.example.dailyquiztest.presentation.main_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

interface Route {
    @Composable
    fun Content(navController: NavController)
}