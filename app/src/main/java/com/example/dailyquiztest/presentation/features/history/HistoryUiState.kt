package com.example.dailyquiztest.presentation.features.history

import androidx.compose.runtime.Composable
import com.example.dailyquiztest.core.Const

interface HistoryUiState {

    @Composable
    fun Display(historyUserActions: HistoryUserActions)

    object EmptyHistoryContDesc : Const.Base("emptyHistoryScreen")
    object NonEmptyHistoryContDesc : Const.Base("nonEmptyHistoryScreen")

    object HistoryLazyList : Const.Base("HistoryLazyList")
}
