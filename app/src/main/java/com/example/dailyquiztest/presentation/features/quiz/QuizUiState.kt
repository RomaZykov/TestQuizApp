package com.example.dailyquiztest.presentation.features.quiz

import androidx.compose.runtime.Composable
import com.example.dailyquiztest.core.Const

interface QuizUiState {

    @Composable
    fun Display(timerProgress: () -> Unit, quizUserActions: QuizUserActions)

    object FiltersContDesc : Const.Base("filtersScreen")
    object QuizContDesc : Const.Base("quizScreen")
    object ResultsContDesc : Const.Base("resultsScreen")

    object ResultsLazyList : Const.Base("ResultLazyList")
}
