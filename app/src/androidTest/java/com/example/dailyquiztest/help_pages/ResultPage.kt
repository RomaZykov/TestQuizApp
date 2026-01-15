package com.example.dailyquiztest.help_pages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import com.example.dailyquiztest.R
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

    fun hasScrollOption() {
        composeTestRule.onNode(
            hasContentDescription(QuizUiState.RESULT_LAZY_LIST)
                    and hasScrollAction()
        )
    }

    fun clickStartAgainButton() {
        startAgainButton.assertIsDisplayed().performClick()
    }

    fun assertFinalResultContains(title: String, desc: String) {
        composeTestRule.onNodeWithText(title).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(desc).assertExists().assertIsDisplayed()
    }

    fun clickBottomStartAgainButton() {
        composeTestRule.onNodeWithTag(QuizUiState.RESULT_LAZY_LIST)
            .performScrollToIndex(4)
        composeTestRule.onNodeWithContentDescription(
            "bottom start again button",
            useUnmergedTree = true
        )
            .performScrollTo()
        clickStartAgainButton()
    }

    fun performScrollToItemWithText(questionTitle: String) {
        composeTestRule.waitUntil {
            composeTestRule.onNodeWithTag(QuizUiState.RESULT_LAZY_LIST)
                .performScrollToNode(hasText(questionTitle))
                .isDisplayed()
        }
    }
}