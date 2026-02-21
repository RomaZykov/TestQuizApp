package com.example.dailyquiztest.helpPages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTouchInput
import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.StringResources
import com.example.dailyquiztest.domain.repository.HistoryRepository
import com.example.testing.stub.stubHistories

class HistoryPage(
    private val composeTestRule: ComposeTestRule,
    private val fakeHistoryRepository: HistoryRepository
) : StringResources() {

    val historyLazyListTag = "history lazy list"
    val nonEmptyContentDesc = "non empty history screen"
    val emptyContentDesc = "empty history screen"

    private val backButton =
        composeTestRule.onNode(
            hasContentDescription(retrieveString(R.string.back_button))
                    and hasClickAction()
        )

    private val startQuizButton =
        composeTestRule.onNode(
            hasContentDescription(retrieveString(R.string.start_quiz_button_text))
                    and hasClickAction()
        )

    private val deleteButton =
        composeTestRule.onNode(
            hasText(retrieveString(R.string.delete_text))

                    and hasClickAction()
        )

    fun assertHistoryTitleWithBackButtonDisplayed() {
        backButton.assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(retrieveString(R.string.history_title_text))
            .assertExists()
            .assertIsDisplayed()
    }

    fun assertHistoryTitleWithBackButtonNotDisplayed() {
        backButton.assertIsNotDisplayed()
        composeTestRule.onNodeWithText(retrieveString(R.string.history_title_text))
            .assertIsNotDisplayed()
    }

    fun clickBackButton() {
        backButton.assertExists().assertIsDisplayed().performClick()
        composeTestRule.waitForIdle()
    }

    private fun clickDeleteButton() {
        deleteButton.assertExists().assertIsDisplayed().performClick()
        composeTestRule.waitForIdle()
    }

    fun clickStartQuizButtonWhenEmptyHistory() {
        assertEmptyHistoriesDisplayed()
        startQuizButton.assertExists().assertIsDisplayed().performClick()
        composeTestRule.waitForIdle()
    }

    fun assertEmptyHistoriesDisplayed() {
        composeTestRule.onNodeWithContentDescription(emptyContentDesc)
            .assertExists().assertIsDisplayed()
    }

    fun assertNonEmptyHistoriesDisplayed() {
        composeTestRule.onNodeWithContentDescription(nonEmptyContentDesc)
            .assertExists().assertIsDisplayed()
    }

    suspend fun initWithDummyHistories() {
        stubHistories.forEach {
            fakeHistoryRepository.saveQuizResult(it)
        }
    }

    fun longPressToDeleteHistoryByNumber(number: Int) {
        composeTestRule.onNodeWithTag(historyLazyListTag).performScrollToNode(
            hasText(retrieveString(R.string.quiz_number_title, number))
        )
        composeTestRule.onNodeWithText(retrieveString(R.string.quiz_number_title, number))
            .assertExists()
            .assertIsDisplayed()
            .performTouchInput {
                longClick(center, 500)
            }
        clickDeleteButton()
    }

    fun assertSnackBarAboutDeletingExist() {
        composeTestRule.onNodeWithText(retrieveString(R.string.delete_retry))
            .assertIsDisplayed()
    }

    fun assertHistoryNonExistWithText(title: String) {
        composeTestRule.onNodeWithText(title).assertDoesNotExist()
        composeTestRule.onNodeWithText(title).assertIsNotDisplayed()
    }
}