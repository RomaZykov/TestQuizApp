package com.example.dailyquiztest.presentation.features.quiz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.main_navigation.Route
import com.example.dailyquiztest.presentation.main_navigation.navigateIfResumed

@Composable
fun QuizScreenNav(
    navController: NavController,
    viewModel: QuizViewModel = hiltViewModel<QuizViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    QuizScreen(
        uiState,
        navController,
        viewModel::prepareQuizGame,
        viewModel::saveQuizAnswer,
        viewModel::retrieveNextAnswer,
        viewModel::showResult,
        viewModel::navigateToWelcome
    )
}

@Composable
fun QuizScreen(
    uiState: QuizUiState,
    navController: NavController,
    prepareQuizGame: (category: CategoriesTypes, difficulty: DifficultiesTypes) -> Unit,
    saveQuizAnswer: (quizUi: QuizUi) -> Unit,
    retrieveNextAnswer: () -> Unit,
    showResult: () -> Unit,
    navigateToWelcome: (toWelcome: (Route) -> Unit) -> Unit
) {
    uiState.Display(userActions = object : UserActions {
        override fun onBackClicked(): () -> Unit = {
            navController.popBackStack()
        }

        override fun onFiltersPhaseNextButtonClicked(): (CategoriesTypes, DifficultiesTypes) -> Unit =
            { category, difficulty ->
                prepareQuizGame.invoke(category, difficulty)
            }

        override fun onNextClicked(): (QuizUi) -> Unit = {
            saveQuizAnswer(it)
            retrieveNextAnswer()
        }

        override fun onResultClicked(): (QuizUi) -> Unit = {
            saveQuizAnswer(it)
            showResult()
        }

        override fun onStartNewQuizClicked(): () -> Unit = {
            navigateToWelcome(navController::navigateIfResumed)
        }
    })
}