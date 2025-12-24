package com.example.dailyquiztest.presentation.features.welcome

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.dailyquiztest.presentation.main_navigation.navigateIfResumed
import com.example.dailyquiztest.presentation.features.welcome.model.Initial
import com.example.dailyquiztest.presentation.main_navigation.HistoryRouteProvider
import com.example.dailyquiztest.presentation.main_navigation.QuizRouteProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface WelcomeViewModel {

    fun welcomeUiStateFlow(): StateFlow<WelcomeUiState>

    fun navigateToFilters(navController: NavController) = Unit

    fun navigateToHistory(navController: NavController) = Unit

    @HiltViewModel
    class Base @Inject constructor(
        private val quizRouteProvider: QuizRouteProvider,
        private val historyRouteProvider: HistoryRouteProvider
    ) : ViewModel(), WelcomeViewModel {

        private val uiState = MutableStateFlow<WelcomeUiState>(Initial)

        override fun welcomeUiStateFlow(): StateFlow<WelcomeUiState> {
            return uiState.asStateFlow()
        }

        override fun navigateToFilters(navController: NavController) {
            navController.navigateIfResumed(quizRouteProvider.route())
        }

        override fun navigateToHistory(navController: NavController) {
            navController.navigateIfResumed(historyRouteProvider.route())
        }
    }
}