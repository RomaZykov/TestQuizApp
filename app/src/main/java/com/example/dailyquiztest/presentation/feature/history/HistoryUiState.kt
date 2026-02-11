package com.example.dailyquiztest.presentation.feature.history

import androidx.compose.runtime.Composable

interface HistoryUiState {

    @Composable
    fun Display(historyUserActions: HistoryUserActions)
}
