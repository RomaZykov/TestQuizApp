package com.example.dailyquiztest.presentation.features.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.dailyquiztest.core.DispatcherList
import com.example.dailyquiztest.domain.repository.HistoryQuizRepository
import com.example.dailyquiztest.presentation.features.history.model.EmptyHistoryUi
import com.example.dailyquiztest.presentation.features.history.model.HistoryUi
import com.example.dailyquiztest.presentation.main_navigation.QuizRouteProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HistoryViewModel {

    fun historyUiStateFlow(): StateFlow<HistoryUiState>

    fun loadQuizHistory()

    fun deleteQuizHistory(quizNumber: Int)

    fun onFiltersPhaseNavigate(navController: NavController)

    @HiltViewModel
    class Base @Inject constructor(
        private val historyQuizRepository: HistoryQuizRepository,
        private val quizRouteProvider: QuizRouteProvider,
        private val dispatchers: DispatcherList
    ) : ViewModel(), HistoryViewModel {
        private val uiState = MutableStateFlow<HistoryUiState>(EmptyHistoryUi)

        override fun historyUiStateFlow(): StateFlow<HistoryUiState> {
            return uiState.asStateFlow()
        }

        override fun loadQuizHistory() {
            viewModelScope.launch(dispatchers.io()) {
                historyQuizRepository.fetchQuizResults().collect {
                    uiState.value = if (it.isEmpty()) {
                        EmptyHistoryUi
                    } else {
                        HistoryUi(historyQuizResults = it)
                    }
                }
            }
        }

        override fun deleteQuizHistory(quizNumber: Int) {
            viewModelScope.launch(dispatchers.io()) {
                historyQuizRepository.deleteQuizResult(quizNumber)
                loadQuizHistory()
            }
        }

        override fun onFiltersPhaseNavigate(navController: NavController) {
            navController.navigate(quizRouteProvider.route())
        }
    }
}