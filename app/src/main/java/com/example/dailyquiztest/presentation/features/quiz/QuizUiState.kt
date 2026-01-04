package com.example.dailyquiztest.presentation.features.quiz

import androidx.compose.runtime.Composable

interface QuizUiState {

    @Composable
    fun Display(userActions: UserActions)

    companion object {
        const val FILTERS_SCREEN = "FiltersScreen"
        const val QUIZ_SCREEN = "QuizScreen"
        const val RESULTS_SCREEN = "ResultsScreen"

        const val TEST_LAZY_RESULTS_ITEMS_COLUMN = "test_lazy_results_items_column"
    }
}