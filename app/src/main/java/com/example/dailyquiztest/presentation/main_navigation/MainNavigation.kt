package com.example.dailyquiztest.presentation.main_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.dailyquiztest.presentation.ui.theme.DailyQuizTheme

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: Any,
    viewModel: NavigationViewModel = hiltViewModel<NavigationViewModel>()
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .background(DailyQuizTheme.colorScheme.primary),
        navController = navController,
        startDestination = startDestination
    ) {
        viewModel.buildRoutes(this, navController)
    }
}

fun NavController.isResumed(): Boolean {
    return currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
}

fun NavController.navigateIfResumed(route: Any) {
    if (isResumed()) {
        navigate(route)
    }
}
