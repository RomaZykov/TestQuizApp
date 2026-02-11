package com.example.dailyquiztest.presentation.feature.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.dailyquiztest.presentation.main_navigation.Route
import com.example.dailyquiztest.presentation.main_navigation.navigateIfResumed

@Composable
fun HistoryScreenNav(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel<HistoryViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadQuizHistory()
    }

    HistoryScreen(
        uiState,
        navController,
        viewModel::onFiltersPhaseNavigate,
        viewModel::deleteQuizHistory
    )
}

@Composable
fun HistoryScreen(
    uiState: HistoryUiState,
    navController: NavController,
    navigateToFilters: (toFilters: (Route) -> Unit) -> Unit,
    deleteQuizHistory: (quizNumber: Int) -> Unit
) {
    uiState.Display(
        historyUserActions = object : HistoryUserActions {
            override fun onBackButtonClicked(): () -> Unit = {
                navController.popBackStack()
            }

            override fun onDeleteClicked(): (Int) -> Unit = {
                deleteQuizHistory.invoke(it)
            }

            override fun onStartQuizClicked(): () -> Unit = {
                navigateToFilters(navController::navigateIfResumed)
            }
        }
    )
}