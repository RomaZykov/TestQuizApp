package com.example.dailyquiztest.pages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
 import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.StringResources
import com.example.dailyquiztest.core.rememberTestNavController
import com.example.dailyquiztest.helpPages.HistoryPage
import com.example.dailyquiztest.presentation.feature.history.HistoryScreen
import com.example.dailyquiztest.presentation.feature.history.model.HistoryUi
import com.example.testing.stub.stubHistories
import com.example.testing.repository.FakeHistoryRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoryPageTest : StringResources() {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var historyPage: HistoryPage
    private val restorationTester: StateRestorationTester = StateRestorationTester(composeTestRule)

    @Before
    fun setUp() {
        historyPage = HistoryPage(composeTestRule, FakeHistoryRepository())
    }

    @Test
    fun firstHistoryTitle_shouldBe_numberOne() {
        restorationTester.setContent {
            val uiState = HistoryUi(histories = listOf(stubHistories.first()))
            HistoryScreen(
                uiState = uiState,
                navController = rememberTestNavController(),
                navigateToFilters = {},
                deleteQuizHistory = {},
            )
        }

        historyPage.assertNonEmptyHistoriesDisplayed()

        composeTestRule.onNodeWithText("Quiz 1").assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText("Quiz 0").isNotDisplayed()
    }

    @Test
    fun changeOrientation_showsAllHistoriesBeforeChanging() {
        restorationTester.setContent {
            val uiState = HistoryUi(histories = stubHistories)
            HistoryScreen(
                uiState = uiState,
                navController = rememberTestNavController(),
                navigateToFilters = {},
                deleteQuizHistory = {},
            )
        }
        historyPage.assertNonEmptyHistoriesDisplayed()

        composeTestRule.onNodeWithTag(historyPage.historyLazyListTag).performScrollToNode(
            hasText(
                retrieveString(
                    R.string.quiz_number_title,
                    stubHistories.last().number
                )
            )
        )

        restorationTester.emulateSavedInstanceStateRestore()

        historyPage.assertNonEmptyHistoriesDisplayed()

        composeTestRule.onNodeWithText(
            retrieveString(
                R.string.quiz_number_title,
                stubHistories.last().number
            )
        ).assertExists().assertIsDisplayed()
    }
}