package com.example.dailyquiztest.helpPages

import androidx.compose.ui.test.ExperimentalTestApi
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
    fun historyLazyListTag() = "history lazy list"

    fun emptyHistoryContentDesc() = retrieveString(R.string.empty_history_screen_cont_desc)

    fun nonEmptyHistoryContentDesc() = retrieveString(R.string.non_empty_history_screen_cont_desc)

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
            hasClickAction()
                    and hasText(retrieveString(R.string.delete_text))
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

    @OptIn(ExperimentalTestApi::class)
    private fun clickDeleteButton() {
        deleteButton.assertExists().assertIsDisplayed().performClick()
        composeTestRule.waitUntilDoesNotExist(
            hasClickAction() and hasText(retrieveString(R.string.delete_text))
        )
    }

    fun clickStartQuizButtonWhenEmptyHistory() {
        assertEmptyHistoriesDisplayed()
        startQuizButton.assertExists().assertIsDisplayed().performClick()
        composeTestRule.waitForIdle()
    }

    fun assertEmptyHistoriesDisplayed() {
        composeTestRule.onNodeWithContentDescription(emptyHistoryContentDesc())
            .assertExists().assertIsDisplayed()
    }

    fun assertNonEmptyHistoriesDisplayed() {
        composeTestRule.onNodeWithContentDescription(nonEmptyHistoryContentDesc())
            .assertExists().assertIsDisplayed()
    }

    suspend fun initWithDummyHistories() {
        stubHistories.forEach {
            fakeHistoryRepository.saveQuizResult(it)
        }
    }

    fun longPressToDeleteHistoryByNumber(number: Int) {
        composeTestRule.onNodeWithTag(historyLazyListTag()).performScrollToNode(
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

    @OptIn(ExperimentalTestApi::class)
    fun assertSnackBarAboutDeletingExist() {
        composeTestRule.onNodeWithContentDescription(retrieveString(R.string.snack_bar_cont_desc))
            .assertIsDisplayed()
        composeTestRule.waitForIdle()
    }

    fun assertHistoryNonExistWithText(title: String) {
        composeTestRule.onNodeWithText(title).assertDoesNotExist()
        composeTestRule.onNodeWithText(title).assertIsNotDisplayed()
    }
}