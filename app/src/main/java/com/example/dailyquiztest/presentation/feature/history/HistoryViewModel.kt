package com.example.dailyquiztest.presentation.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyquiztest.core.DispatcherList
import com.example.dailyquiztest.domain.repository.HistoryRepository
import com.example.dailyquiztest.presentation.feature.history.model.EmptyHistoryUi
import com.example.dailyquiztest.presentation.feature.history.model.HistoryUi
import com.example.dailyquiztest.presentation.main_navigation.QuizRouteProvider
import com.example.dailyquiztest.presentation.main_navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val quizRouteProvider: QuizRouteProvider,
    private val dispatchers: DispatcherList
) : ViewModel(), HistoryCoreVMActions {
    private val uiStateMutable = MutableStateFlow<HistoryUiState>(EmptyHistoryUi)
    val uiState: StateFlow<HistoryUiState>
        get() = uiStateMutable.asStateFlow()

    override fun loadQuizHistory() {
        viewModelScope.launch(dispatchers.io()) {
            historyRepository.fetchQuizResults().collect {
                uiStateMutable.value = if (it.isEmpty()) {
                    EmptyHistoryUi
                } else {
                    HistoryUi(histories = it)
                }
            }
        }
    }

    override fun deleteQuizHistory(quizNumber: Int) {
        viewModelScope.launch(dispatchers.io()) {
            historyRepository.deleteQuizResult(quizNumber)
            loadQuizHistory()
        }
    }

    override fun onFiltersPhaseNavigate(toFilters: (Route) -> Unit) {
        toFilters.invoke(quizRouteProvider.route())
    }
}

interface HistoryCoreVMActions {
    fun loadQuizHistory()

    fun deleteQuizHistory(quizNumber: Int)

    fun onFiltersPhaseNavigate(toFilters: (Route) -> Unit)
}