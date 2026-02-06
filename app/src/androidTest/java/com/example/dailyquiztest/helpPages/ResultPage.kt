package com.example.dailyquiztest.helpPages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.StringResources
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState

class ResultPage(private val composeTestRule: ComposeTestRule) : StringResources() {
    val resultLazyListTag = "result lazy list"
    val mainContentDesc = "result screen"

    private val startAgainButton =
        composeTestRule.onNode(
            hasText(retrieveString(R.string.start_again))
                    and hasClickAction()
        )

    fun assertPageDisplayed() {
        composeTestRule.onNodeWithContentDescription(mainContentDesc)
            .assertExists()
            .assertIsDisplayed()
    }

    fun hasScrollOption() {
        composeTestRule.onNode(
            hasTestTag(resultLazyListTag)
                    and hasScrollAction()
        )
    }

    fun clickTopStartAgainButton() {
        startAgainButton.performScrollTo().assertIsDisplayed().performClick()
        composeTestRule.waitForIdle()
    }

    fun assertFinalResultContains(title: String, desc: String) {
        composeTestRule.onNodeWithText(title).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(desc).assertExists().assertIsDisplayed()
    }

    fun clickBottomStartAgainButton() {
        composeTestRule.onNodeWithTag(resultLazyListTag).performScrollToNode(
            hasContentDescription("bottom start again button")
        )
        composeTestRule.onNodeWithContentDescription("bottom start again button")
            .assertExists().assertIsDisplayed().performClick()
        composeTestRule.waitForIdle()
    }

    fun performScrollToItemWithText(questionTitle: String) {
        composeTestRule.waitUntil {
            composeTestRule.onNodeWithTag(resultLazyListTag)
                .performScrollToNode(hasText(questionTitle))
                .isDisplayed()
        }
    }
}