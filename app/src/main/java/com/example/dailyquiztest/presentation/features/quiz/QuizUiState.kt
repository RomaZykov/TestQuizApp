package com.example.dailyquiztest.presentation.features.quiz

import androidx.compose.runtime.Composable

interface QuizUiState {

    @Composable
    fun Display(timerProgress: () -> Unit, quizUserActions: QuizUserActions)
}
