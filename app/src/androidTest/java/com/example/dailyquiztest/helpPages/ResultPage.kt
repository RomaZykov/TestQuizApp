package com.example.dailyquiztest.helpPages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
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

    // Card icon
    fun assertGreenCardIconDisplayedOnQuestion(question: String) {
        composeTestRule.onNodeWithText(question).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("correct card icon")
            .assertExists().assertIsDisplayed()
    }

    fun assertRedCardIconDisplayedOnQuestion(question: String) {
        composeTestRule.onNodeWithText(question).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("wrong card icon")
            .assertExists().assertIsDisplayed()
    }

    fun assertCardIconsNotDisplayedOnQuestion(question: String) {
        composeTestRule.onNodeWithText(question).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("wrong card icon")
            .assertIsNotDisplayed()
        composeTestRule.onNodeWithContentDescription("correct card icon")
            .assertIsNotDisplayed()
    }

    // Edge board
    fun assertGreenEdgeDisplayedOnText(userAnswer: String) {
        composeTestRule.onNodeWithText(userAnswer, ignoreCase = true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag("green edge")
            .assertExists().assertIsDisplayed()
    }

    fun assertGreenEdgeIsNotDisplayedOnText(userAnswer: String) {
        composeTestRule.onNodeWithText(userAnswer, ignoreCase = true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag("green edge")
            .assertIsNotDisplayed()
    }

    fun assertRedEdgeDisplayedOnText(userAnswer: String) {
        composeTestRule.onNodeWithText(userAnswer, ignoreCase = true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag("red edge")
            .assertExists().assertIsDisplayed()
    }

    fun assertRedEdgeIsNotDisplayedOnText(option: String) {
        composeTestRule.onNodeWithText(option, ignoreCase = true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag("red edge")
            .assertIsNotDisplayed()
    }

    // Option icon
    fun assertGreenOptionIconDisplayedOnText(userAnswer: String) {
        composeTestRule.onNodeWithText(userAnswer, ignoreCase = true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("correct option icon")
            .assertExists().assertIsDisplayed()
    }

    fun assertGreenOptionIconIsNotDisplayedOnText(userAnswer: String) {
        composeTestRule.onNodeWithText(userAnswer, ignoreCase = true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("correct option icon")
            .assertIsNotDisplayed()
    }

    fun assertRedOptionIconDisplayedOnText(userAnswer: String) {
        composeTestRule.onNodeWithText(userAnswer, ignoreCase = true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("wrong option icon")
            .assertExists().assertIsDisplayed()
    }

    fun assertRedOptionIconIsNotDisplayedOnText(userAnswer: String) {
        composeTestRule.onNodeWithText(userAnswer, ignoreCase = true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("wrong option icon")
            .assertIsNotDisplayed()
    }
}