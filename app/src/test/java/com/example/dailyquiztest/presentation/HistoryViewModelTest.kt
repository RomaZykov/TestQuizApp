package com.example.dailyquiztest.presentation

import com.example.testing.dummy.dummyHistoryResults
import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.domain.model.QuizResult
import com.example.dailyquiztest.testdoubles.FakeQuizRouteProvider
import com.example.dailyquiztest.presentation.features.history.HistoryUiState
import com.example.dailyquiztest.presentation.features.history.HistoryViewModel
import com.example.dailyquiztest.presentation.features.history.model.EmptyHistoryUi
import com.example.dailyquiztest.presentation.features.history.model.HistoryUi
import com.example.testing.di.FakeDispatcherList
import com.example.testing.repository.FakeHistoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HistoryViewModelTest {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var fakeHistoryRepository: FakeHistoryRepository
    private lateinit var fakeQuizRouteProvider: FakeQuizRouteProvider
    private lateinit var dispatchers: FakeDispatcherList
    private lateinit var stateFlow: StateFlow<HistoryUiState>

    @OptIn(ExperimentalCoroutinesApi::class)
    private var testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        fakeHistoryRepository = FakeHistoryRepository()
        fakeQuizRouteProvider = FakeQuizRouteProvider()
        dispatchers = FakeDispatcherList(testDispatcher)
        viewModel = HistoryViewModel(
            historyRepository = fakeHistoryRepository,
            quizRouteProvider = fakeQuizRouteProvider,
            dispatchers = dispatchers
        )
        stateFlow = viewModel.uiState
    }

    @Test
    fun `when there are histories historyUiStateFlow should be HistoryUi with these histories`() =
        runTest {
            initDummyHistories()

            viewModel.loadQuizHistory()

            assertTrue(dispatchers.wasIoCalled)
            assertFalse(dispatchers.wasUiCalled)

            val expectedUiState = HistoryUi(dummyHistoryResults)
            assertEquals(expectedUiState, stateFlow.value)
        }

    @Test
    fun `default value of historyUiStateFlow should be EmptyHistoryUi`() = runTest {
        viewModel.loadQuizHistory()

        assertTrue(dispatchers.wasIoCalled)
        assertFalse(dispatchers.wasUiCalled)

        val expectedUiState = EmptyHistoryUi
        assertEquals(expectedUiState, stateFlow.value)
    }

    @Test
    fun `delete all histories should show empty history state`() = runTest {
        initDummyHistories()
        viewModel.loadQuizHistory()

        assertTrue(dispatchers.wasIoCalled)
        assertFalse(dispatchers.wasUiCalled)

        var countOfHistory = 0
        repeat(5) {
            viewModel.deleteQuizHistory(countOfHistory++)
        }

        val expectedUiState = EmptyHistoryUi
        assertEquals(expectedUiState, stateFlow.value)
    }

    @Test
    fun `delete all histories except from middle should show that only one history`() = runTest {
        initDummyHistories()
        viewModel.loadQuizHistory()

        assertTrue(dispatchers.wasIoCalled)
        assertFalse(dispatchers.wasUiCalled)

        viewModel.deleteQuizHistory(0)
        viewModel.deleteQuizHistory(4)
        viewModel.deleteQuizHistory(2)
        viewModel.deleteQuizHistory(3)

        val expectedUiState = HistoryUi(
            listOf(
                QuizResult(
                    id = 1,
                    stars = 0,
                    category = Category.GENERAL_KNOWLEDGE,
                    difficulty = Difficulty.EASY,
                    lastTime = "00:00",
                    lastDate = "2025"
                )
            )
        )
        assertEquals(expectedUiState, stateFlow.value)
    }

    private suspend fun initDummyHistories() =
        dummyHistoryResults.forEach {
            fakeHistoryRepository.saveQuizResult(it)
        }
}
