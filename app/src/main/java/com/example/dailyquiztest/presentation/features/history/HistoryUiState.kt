package com.example.dailyquiztest.presentation.features.history

import androidx.compose.runtime.Composable

interface HistoryUiState {

    @Composable
    fun Display(historyUserActions: HistoryUserActions)
}
