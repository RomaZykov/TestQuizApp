package com.example.dailyquiztest.presentation.features.quiz

import androidx.compose.runtime.Composable

interface QuizUiState {

    @Composable
    fun Display(quizUserActions: QuizUserActions)

    companion object {
        const val FILTERS_SCREEN = "FiltersScreen"
        const val QUIZ_SCREEN = "QuizScreen"
        const val RESULTS_SCREEN = "ResultsScreen"

        const val RESULT_LAZY_LIST = "ResultLazyList"
    }
}