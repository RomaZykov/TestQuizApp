package com.example.dailyquiztest

import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyquiztest.core.dummyHistoryResults
import com.example.dailyquiztest.core.rememberTestNavController
import com.example.dailyquiztest.pages.HistoryPage
import com.example.dailyquiztest.presentation.features.history.HistoryScreen
import com.example.dailyquiztest.presentation.features.history.HistoryUiState
import com.example.dailyquiztest.presentation.features.history.HistoryViewModel
import com.example.dailyquiztest.presentation.features.history.model.HistoryUi
import com.example.testing.repository.FakeHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoryPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var historyPage: HistoryPage
    private val restorationTester: StateRestorationTester = StateRestorationTester(composeTestRule)

    @Before
    fun setUp() {
        historyPage = HistoryPage(composeTestRule, FakeHistoryRepository())
    }

    @Test
    fun changeOrientationInHistory() {
        restorationTester.setContent {
            HistoryScreen(rememberTestNavController(), object : HistoryViewModel {
                private val uiState = MutableStateFlow<HistoryUiState>(HistoryUi(dummyHistoryResults))
                override fun historyUiStateFlow(): StateFlow<HistoryUiState> {
                    return uiState.asStateFlow()
                }

                override fun loadQuizHistory() {
                }

                override fun deleteQuizHistory(quizNumber: Int) {
                }

                override fun onFiltersPhaseNavigate(navController: NavController) {
                }
            })
        }

        historyPage.assertNonEmptyHistoriesDisplayed()

        restorationTester.emulateSavedInstanceStateRestore()

        historyPage.assertNonEmptyHistoriesDisplayed()
    }
}