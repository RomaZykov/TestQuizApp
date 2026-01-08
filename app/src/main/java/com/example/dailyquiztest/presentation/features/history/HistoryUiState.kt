package com.example.dailyquiztest.presentation.features.history

import androidx.compose.runtime.Composable

interface HistoryUiState {

    @Composable
    fun Display(historyUserActions: HistoryUserActions)

    companion object {
        const val EMPTY_HISTORY_SCREEN = "emptyHistoryScreen"
        const val NON_EMPTY_HISTORY_SCREEN = "nonEmptyHistoryScreen"
    }
}
