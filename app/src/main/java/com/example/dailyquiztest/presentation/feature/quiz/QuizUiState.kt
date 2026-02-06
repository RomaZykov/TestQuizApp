package com.example.dailyquiztest.presentation.feature.quiz

import androidx.compose.runtime.Composable

interface QuizUiState {

    @Composable
    fun Display(timerProgress: () -> Unit, quizUserActions: QuizUserActions)
}
