package com.example.dailyquiztest.presentation

import com.example.dailyquiztest.testdoubles.FakeHistoryRouteProvider
import com.example.dailyquiztest.testdoubles.FakeQuizRouteProvider
import com.example.dailyquiztest.presentation.features.welcome.WelcomeViewModel
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class WelcomeViewModelTest {

    private lateinit var viewModel: WelcomeViewModel

    private lateinit var fakeQuizRouteProvider: FakeQuizRouteProvider

    private lateinit var fakeHistoryRouteProvider: FakeHistoryRouteProvider

    @Before
    fun setUp() {
        fakeQuizRouteProvider = FakeQuizRouteProvider()
        fakeHistoryRouteProvider = FakeHistoryRouteProvider()
        viewModel = WelcomeViewModel(
            quizRouteProvider = fakeQuizRouteProvider,
            historyRouteProvider = fakeHistoryRouteProvider
        )
    }

    @Test
    fun `start quiz navigates to filters route`() {
        viewModel.navigateToFilters {}

        assertTrue(fakeQuizRouteProvider.wasRouteCalled)
    }

    @Test
    fun `history button navigates to history route`() {
        viewModel.navigateToHistory {}

        assertTrue(fakeHistoryRouteProvider.wasRouteCalled)
    }
}