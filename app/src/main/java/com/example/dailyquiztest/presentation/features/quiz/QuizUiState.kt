package com.example.dailyquiztest.presentation.features.quiz

import androidx.compose.runtime.Composable
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi

interface QuizUiState {

    @Composable
    fun Display(
        onFiltersPhaseNextButtonClicked: (CategoriesTypes, DifficultiesTypes) -> Unit,
        onNextClicked: (QuizUi) -> Unit,
        onBackClicked: () -> Unit,
        onResultClicked: (QuizUi) -> Unit,
        onStartNewQuizClicked: () -> Unit
    )

    companion object {
        const val FILTERS_SCREEN = "FiltersScreen"
        const val QUIZ_SCREEN = "QuizScreen"
        const val RESULTS_SCREEN = "ResultsScreen"

        const val TEST_LAZY_RESULTS_ITEMS_COLUMN = "test_lazy_results_items_column"
    }
}