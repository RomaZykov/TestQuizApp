package com.example.dailyquiztest.presentation.features.history.model

import androidx.compose.runtime.Composable
import com.example.dailyquiztest.presentation.common.loading.UiLoading
import com.example.dailyquiztest.presentation.features.history.HistoryUiState

object LoadingUi : HistoryUiState {
    @Composable
    override fun Display(
        onStartQuizClicked: () -> Unit,
        onDeleteClicked: (Int) -> Unit,
        onBackButtonClicked: () -> Unit
    ) {
        UiLoading()
    }
}