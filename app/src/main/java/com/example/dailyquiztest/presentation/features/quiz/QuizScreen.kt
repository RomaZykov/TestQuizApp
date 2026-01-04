package com.example.dailyquiztest.presentation.features.quiz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.main_navigation.navigateIfResumed

@Composable
fun QuizScreen(
    navController: NavController,
    viewModel: QuizViewModel = hiltViewModel<QuizViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    uiState.Display(userActions = object : UserActions {
        override fun onBackClicked(): () -> Unit = {
            navController.popBackStack()
        }

        override fun onFiltersPhaseNextButtonClicked(): (CategoriesTypes, DifficultiesTypes) -> Unit = { category, difficulty ->
            viewModel.prepareQuizGame(category, difficulty)
        }

        override fun onNextClicked(): (QuizUi) -> Unit = {
            viewModel.saveQuizAnswer(it)
            viewModel.retrieveNextAnswer()
        }

        override fun onResultClicked(): (QuizUi) -> Unit = {
            viewModel.saveQuizAnswer(it)
            viewModel.showResult()
        }

        override fun onStartNewQuizClicked(): () -> Unit = {
            viewModel.navigateToWelcome(navController::navigateIfResumed)
        }
    })
}