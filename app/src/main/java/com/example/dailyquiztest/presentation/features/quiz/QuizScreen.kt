package com.example.dailyquiztest.presentation.features.quiz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun QuizScreen(
    navController: NavController,
    viewModel: QuizViewModel = hiltViewModel<QuizViewModel.Base>()
) {
    val uiState by viewModel.quizUiStateFlow().collectAsStateWithLifecycle()
    uiState.Display(
        onFiltersPhaseNextButtonClicked = { category, difficulty ->
            viewModel.prepareQuizGame(category, difficulty)
        },
        onNextClicked = {
            viewModel.saveQuizAnswers(it)
            viewModel.retrieveNextAnswer()
        },
        onBackClicked = {
            navController.popBackStack()
        },
        onResultClicked = {
            viewModel.saveQuizAnswers(it)
            viewModel.showResult()
        },
        onStartNewQuizClicked = {
            viewModel.navigateToWelcome(navController)
        }
    )
}