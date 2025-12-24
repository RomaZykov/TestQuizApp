package com.example.dailyquiztest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import com.example.dailyquiztest.core.StringResources
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState

class ResultPage(private val composeTestRule: ComposeTestRule) : StringResources() {
    private val startAgainButton =
        composeTestRule.onNode(
            hasText(retrieveString(R.string.start_again))
                    and hasClickAction()
        )

    fun assertPageDisplayed() {
        composeTestRule.onNodeWithContentDescription(QuizUiState.RESULTS_SCREEN)
            .assertExists()
            .assertIsDisplayed()
    }

    fun clickStartAgainButton() {
        startAgainButton.assertIsDisplayed().performClick()
    }

    fun assertFinalResultContains(title: String, desc: String) {
        composeTestRule.onNodeWithText(title).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(desc).assertExists().assertIsDisplayed()
    }

    fun clickBottomStartAgainButton() {
        composeTestRule.onNodeWithTag(QuizUiState.TEST_LAZY_RESULTS_ITEMS_COLUMN).performScrollToIndex(4)
        composeTestRule.onNodeWithContentDescription("bottom start again button", useUnmergedTree = true)
            .performScrollTo()
        clickStartAgainButton()
    }
}
