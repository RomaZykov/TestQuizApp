package com.example.dailyquiztest.presentation.features.welcome

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.dailyquiztest.presentation.main_navigation.navigateIfResumed
import com.example.dailyquiztest.presentation.features.welcome.model.Initial
import com.example.dailyquiztest.presentation.main_navigation.HistoryRouteProvider
import com.example.dailyquiztest.presentation.main_navigation.QuizRouteProvider
import com.example.dailyquiztest.presentation.main_navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val quizRouteProvider: QuizRouteProvider,
    private val historyRouteProvider: HistoryRouteProvider
) : ViewModel(), ViewModelActions {

    private val uiStateMutable = MutableStateFlow<WelcomeUiState>(Initial)
    val uiState: StateFlow<WelcomeUiState>
        get() = uiStateMutable.asStateFlow()

    override fun navigateToFilters(toFilters: (Route) -> Unit) {
        toFilters.invoke(quizRouteProvider.route())
    }

    override fun navigateToHistory(toHistory: (Route) -> Unit) {
        toHistory.invoke(historyRouteProvider.route())
    }
}

interface ViewModelActions {
    fun navigateToFilters(toFilters: (Route) -> Unit)

    fun navigateToHistory(toHistory: (Route) -> Unit)
}
