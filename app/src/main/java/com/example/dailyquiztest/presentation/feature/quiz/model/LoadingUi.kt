package com.example.dailyquiztest.presentation.feature.quiz.model

import androidx.compose.runtime.Composable
import com.example.dailyquiztest.presentation.common.loading.UiLoading
import com.example.dailyquiztest.presentation.feature.quiz.QuizUiState
import com.example.dailyquiztest.presentation.feature.quiz.QuizUserActions

object LoadingUi : QuizUiState {

    @Composable
    override fun Display(quizUserActions: QuizUserActions) {
        UiLoading()
    }
}
