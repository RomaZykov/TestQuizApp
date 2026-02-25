package com.example.dailyquiztest.presentation.feature.quiz

import androidx.compose.runtime.Composable
import com.example.dailyquiztest.domain.CalculateScore

interface QuizUiState {

    @Composable
    fun Display(quizUserActions: QuizUserActions)

    fun visit(score: CalculateScore.AddInfo) = Unit
}
