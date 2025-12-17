package com.example.dailyquiztest.presentation.features.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel<HistoryViewModel.Base>()
) {
    val uiState by viewModel.historyUiStateFlow().collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.loadQuizHistory()
    }
    uiState.Display(
        onStartQuizClicked = {
            viewModel.onFiltersPhaseNavigate(navController)
        },
        onDeleteClicked = {
            viewModel.deleteQuizHistory(it)
        },
        onBackButtonClicked = {
            navController.popBackStack()
        }
    )
}