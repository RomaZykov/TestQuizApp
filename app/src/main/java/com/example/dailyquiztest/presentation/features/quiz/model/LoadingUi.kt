package com.example.dailyquiztest.presentation.features.quiz.model

import androidx.compose.runtime.Composable
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.presentation.common.loading.UiLoading
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState

object LoadingUi : QuizUiState {

    @Composable
    override fun Display(
        onFiltersPhaseNextButtonClicked: (CategoriesTypes, DifficultiesTypes) -> Unit,
        onNextClicked: (QuizUi) -> Unit,
        onBackClicked: () -> Unit,
        onResultClicked: (QuizUi) -> Unit,
        onStartNewQuizClicked: () -> Unit
    ) {
        UiLoading()
    }
}
