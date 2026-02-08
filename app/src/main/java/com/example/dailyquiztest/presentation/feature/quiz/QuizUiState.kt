package com.example.dailyquiztest.presentation.feature.quiz

import androidx.compose.runtime.Composable

interface QuizUiState {

    @Composable
    fun Display(quizUserActions: QuizUserActions)

    fun visit(score: CalculateScore.AddInfo) = Unit
}
