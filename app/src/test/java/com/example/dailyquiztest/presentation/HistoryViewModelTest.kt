package com.example.dailyquiztest.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.dailyquiztest.domain.repository.HistoryQuizRepository
import com.example.dailyquiztest.presentation.features.history.HistoryUiState
import com.example.dailyquiztest.presentation.features.history.HistoryViewModel
import com.example.dailyquiztest.presentation.features.history.model.EmptyHistoryUi
import com.example.dailyquiztest.presentation.main_navigation.QuizRouteProvider
import com.example.dailyquiztest.presentation.main_navigation.Route
import com.example.testing.di.FakeDispatcherList
import com.example.testing.repository.FakeHistoryRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HistoryViewModelTest {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var fakeHistoryRepository: HistoryQuizRepository
    private lateinit var fakeQuizRouteProvider: QuizRouteProvider
    private lateinit var dispatchers: FakeDispatcherList
    private lateinit var stateFlow: StateFlow<HistoryUiState>

    @Before
    fun setup() {
        fakeHistoryRepository = FakeHistoryRepository()
        fakeQuizRouteProvider = FakeQuizRouteProvider()
        dispatchers = FakeDispatcherList()
        viewModel = HistoryViewModel.Base(
            historyQuizRepository = fakeHistoryRepository,
            quizRouteProvider = fakeQuizRouteProvider,
            dispatchers = dispatchers
        )
        stateFlow = viewModel.historyUiStateFlow()
    }

    @Test
    fun loadEmptyHistoryPage_whenNoHistories() = runTest {
        (fakeHistoryRepository as FakeHistoryRepository).clearHistories()
        viewModel.loadQuizHistory()

        assertTrue(dispatchers.wasIoCalled)
        assertFalse(dispatchers.wasUiCalled)

        val expectedUiState = EmptyHistoryUi
        assertEquals(expectedUiState, stateFlow.value)
    }

    private class FakeQuizRouteProvider : QuizRouteProvider {
        var wasRouteCalled = false
        override fun route(): Route {
            wasRouteCalled = true
            return object : Route {
                @Composable
                override fun Content(navController: NavController) = Unit
            }
        }
    }
}
