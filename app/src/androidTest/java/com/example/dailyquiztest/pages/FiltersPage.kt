package com.example.dailyquiztest.pages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.dailyquiztest.R
import com.example.dailyquiztest.core.StringResources
import com.example.dailyquiztest.domain.model.CategoriesTypes
import com.example.dailyquiztest.domain.model.DifficultiesTypes
import com.example.dailyquiztest.presentation.features.quiz.QuizUiState

class FiltersPage(private val composeTestRule: ComposeTestRule) :
    StringResources() {

    private val startButton =
        composeTestRule.onNode(
            hasText(retrieveString(R.string.start_quiz_button_text))
                    and hasClickAction()
        )

    fun assertStartQuizButtonNotEnabled() {
        startButton.assertIsDisplayed()
        startButton.assertIsNotEnabled()
    }

    fun assertStartQuizButtonEnabled() {
        startButton.assertIsDisplayed()
        startButton.assertIsEnabled()
    }

    fun assertPageDisplayed() {
        composeTestRule.onNodeWithContentDescription(QuizUiState.FILTERS_SCREEN).assertExists()
            .assertIsDisplayed()
    }

    fun chooseSomeCategory(someCategory: CategoriesTypes) {
        composeTestRule.onNodeWithText(retrieveString(R.string.category_menu_text))
            .assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText(someCategory.categoryName).assertIsDisplayed().performClick()
    }

    fun chooseSomeDifficulty(someDifficulty: DifficultiesTypes) {
        composeTestRule.onNodeWithText(retrieveString(R.string.difficulty_menu_text))
            .assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText(someDifficulty.levelApi).assertIsDisplayed().performClick()
    }

    fun clickStartQuizButton() {
        startButton.assertIsDisplayed()
        startButton.performClick()
    }

    fun errorSnackBarWasDisplayed() {
        composeTestRule.onNodeWithText(retrieveString(R.string.error_message))
            .assertExists()
            .assertIsDisplayed()
    }
}
