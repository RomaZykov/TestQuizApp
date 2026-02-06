package com.example.dailyquiztest.presentation.feature.welcome

import androidx.lifecycle.ViewModel
import com.example.dailyquiztest.presentation.main_navigation.HistoryRouteProvider
import com.example.dailyquiztest.presentation.main_navigation.QuizRouteProvider
import com.example.dailyquiztest.presentation.main_navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val quizRouteProvider: QuizRouteProvider,
    private val historyRouteProvider: HistoryRouteProvider
) : ViewModel(), ViewModelActions {

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
