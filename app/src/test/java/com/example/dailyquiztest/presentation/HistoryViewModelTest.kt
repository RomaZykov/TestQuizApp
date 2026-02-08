package com.example.dailyquiztest.presentation

import com.example.testing.stub.stubHistories
import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.domain.model.ResultDomain
import com.example.dailyquiztest.fake.FakeQuizRouteProvider
import com.example.dailyquiztest.presentation.feature.history.HistoryUiState
import com.example.dailyquiztest.presentation.feature.history.HistoryViewModel
import com.example.dailyquiztest.presentation.feature.history.model.EmptyHistoryUi
import com.example.dailyquiztest.presentation.feature.history.model.HistoryUi
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

            val expectedUiState = HistoryUi(stubHistories)
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
        repeat(7) {
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
        viewModel.deleteQuizHistory(5)
        viewModel.deleteQuizHistory(6)

        val expectedUiState = HistoryUi(
            listOf(
                ResultDomain.Result(
                    number = 1,
                    stars = 0,
                    categoryDomain = CategoryDomain.GENERAL_KNOWLEDGE,
                    difficultyDomain = DifficultyDomain.EASY,
                    lastTime = "00:00",
                    lastDate = "2025"
                )
            )
        )
        assertEquals(expectedUiState, stateFlow.value)
    }

    private suspend fun initDummyHistories() =
        stubHistories.forEach {
            fakeHistoryRepository.saveQuizResult(it)
        }
}
