package com.example.dailyquiztest.pages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTouchInput
import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.StringResources
import com.example.dailyquiztest.core.dummyHistoryResults
import com.example.dailyquiztest.domain.repository.HistoryRepository
import com.example.dailyquiztest.presentation.features.history.HistoryUiState

class HistoryPage(
    private val composeTestRule: ComposeTestRule,
    private val fakeHistoryRepository: HistoryRepository,
) : StringResources() {

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

    fun clickBackButton() {
        backButton.assertIsDisplayed()
        backButton.performClick()
    }

    private fun clickDeleteButton() {
        deleteButton.assertIsDisplayed()
        deleteButton.performClick()
    }

    fun clickStartQuizButtonWhenEmptyHistory() {
        assertEmptyHistoriesDisplayed()
        startQuizButton.assertIsDisplayed()
        startQuizButton.performClick()
    }

    fun assertEmptyHistoriesDisplayed() {
        composeTestRule.onNodeWithContentDescription(HistoryUiState.EMPTY_HISTORY_SCREEN)
            .assertExists().assertIsDisplayed()
    }

    fun assertNonEmptyHistoriesDisplayed() {
        composeTestRule.onNodeWithContentDescription(HistoryUiState.NON_EMPTY_HISTORY_SCREEN)
            .assertExists().assertIsDisplayed()
    }

    suspend fun initWithDummyHistories() {
        dummyHistoryResults.forEach {
            fakeHistoryRepository.saveQuizResult(it)
        }
    }

    fun longPressToDeleteHistoryByIndex(id: Int) {
        val historyToDelete =
            composeTestRule.onNodeWithText(retrieveString(R.string.quiz_number_title, id + 1))
        historyToDelete.performScrollTo().assertExists().assertIsDisplayed().performTouchInput {
            longClick()
        }
        clickDeleteButton()
    }

    fun assertSnackBarAboutDeletingExist() {
        composeTestRule.onNodeWithText(retrieveString(R.string.delete_retry))
            .assertIsDisplayed()
    }

    fun assertHistoryNonExistWithText(title: String) {
        composeTestRule.onNodeWithText(title).assertDoesNotExist()
    }
}